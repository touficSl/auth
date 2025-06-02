package com.service.auth.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.service.auth.builder.request.EmailDetailsRq;
import com.service.auth.builder.request.LoginRequest;
import com.service.auth.builder.request.RegisterRequest;
import com.service.auth.builder.request.SMSDetailsRq;
import com.service.auth.builder.request.TeamRq;
import com.service.auth.builder.request.UpdateUserReq;
import com.service.auth.builder.response.CountResponse;
import com.service.auth.builder.response.DatatableResponse;
import com.service.auth.builder.response.JwtResponse;
import com.service.auth.builder.response.LdapResponse;
import com.service.auth.builder.response.MenuResponse;
import com.service.auth.builder.response.MessageResponse;
import com.service.auth.builder.response.RolesUsersResponse;
import com.service.auth.builder.response.TeamsRolesResponse;
import com.service.auth.builder.response.TeamsRolesUsersResponse;
import com.service.auth.builder.response.TokenResponse;
import com.service.auth.builder.response.UserResponse;
import com.service.auth.builder.response.VerifyUsernameResponse;
import com.service.auth.config.Constants;
import com.service.auth.config.JwtUtils;
import com.service.auth.config.Utils;
import com.service.auth.enumeration.AuthTypeEnum;
import com.service.auth.enumeration.OTPTypeEnum;
import com.service.auth.model.Authorization;
import com.service.auth.model.MenuRole;
import com.service.auth.model.Roles;
import com.service.auth.model.Settings;
import com.service.auth.model.Teams;
import com.service.auth.model.Tokens;
import com.service.auth.model.Users;
import com.service.auth.repository.AuthorizationRepository;
import com.service.auth.repository.MenuRoleRepository;
import com.service.auth.repository.RoleRepository;
import com.service.auth.repository.TeamsRepository;
import com.service.auth.repository.UsersRepository;
import com.service.auth.rest.call.UAEPassUserInfo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SettingsService settingsService;
	
	@Autowired
	UsersRepository usersRepository;

	@Autowired
	AuthorizationRepository authorizationRepository;

	@Autowired
	RoleRepository rolesRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	LdapServiceImpl ldapService;

	@Autowired
	GoogleRecaptchaService googleRecaptchaService;

    @Autowired
    private MessageService messageService;

	@Autowired
	EmailService emailService;

	@Autowired
	SMSService smsService;
	
	@Autowired
	TokensService tokensService;
	
	@Autowired
	MenuRoleRepository menuRoleRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	TeamsRepository teamsRepository;

	@Override
	public ResponseEntity<?> authenticate(Locale locale, LoginRequest loginRequest, boolean verifyotptoken, String device, String ip, HttpServletResponse response) {

		try {
	   	    Settings settings = settingsService.returndefaultSettings();
			if (settings.isRecaptchavalidation() == true) {
				ResponseEntity<?> verifycaptcha = googleRecaptchaService.verifyRecaptcha(locale, loginRequest.getCaptchaToken());
				if (verifycaptcha != null) 
					return verifycaptcha;
			}
	
			Optional<Users> userEntity = findByUsername(loginRequest.getUsername());
			if (userEntity.isPresent()) {
				Users user = userEntity.get();
				
				if (!user.isEnable())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_creds", locale), 119));
				
				if (user.isLocked())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("account_locked", locale), 116));
					
				boolean sendsms = user.getMobile_no() == null || user.getMobile_no().isEmpty() ? false : true;
	
				Roles role = rolesRepository.findByUserRole(user.getUser_role());
	
				String expirydate = Utils.convertDateToString(jwtUtils.returnJwtExpriyDate(true), Constants.DATETIME_FORMAT);
				if (role != null && role.getAuth_type().equals(AuthTypeEnum.LDAP.name())) {
	
					ResponseEntity<?> ldapauthres = ldapService.authenticate(locale, loginRequest.getUsername(), loginRequest.getPassword());
					if (ldapauthres != null && ldapauthres.getBody() instanceof MessageResponse)
						if (user.isBypass3rdpartyauth() && 
							user.getPassword() != null && !user.getPassword().isEmpty() && 
							encoder.matches(loginRequest.getPassword(), user.getPassword())) 
							// continue
							System.out.println("Bypass third party authentication.");
						else
							return ldapauthres;
					
					if (!role.getRequire2fa()) {
						return authuser(device, ip, user, locale, response, role);
					}
					
					return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(Constants.VERIFY_OTP_TOKEN + loginRequest.getUsername(), null),
						user.getUser_id(), user.getUsername(), user.getEmail(), user.getUser_role(), sendsms, expirydate, getuserfullname(user, locale), getuserfullnamear(user, locale), role));
				}
	
				if (role != null && Utils.isrequiredpass(role.getAuth_type())) {
					if (user.getPassword() != null && encoder.matches(loginRequest.getPassword(), user.getPassword())) {
						if (!role.getRequire2fa()) {
							return authuser(device, ip, user, locale, response, role);
						}
						if (user.getInvalidattempts() > 0) {
							user.setInvalidattempts(0);
							usersRepository.save(user);
						}
							
						return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(Constants.VERIFY_OTP_TOKEN + loginRequest.getUsername(), null),
							user.getUser_id(), user.getUsername(), user.getEmail(), user.getUser_role(), sendsms, expirydate, getuserfullname(user, locale), getuserfullnamear(user, locale), role));
					} else {
						user.setInvalidattempts(user.getInvalidattempts() + 1);
						if (user.getInvalidattempts() >= settings.getMaximuminvalidattempts())
							user.setLocked(true);
						usersRepository.save(user);

						if (user.getInvalidattempts() >= settings.getMaximuminvalidattempts())
							return ResponseEntity.ok(new MessageResponse(messageService.getMessage("account_locked", locale), 116));
							
						return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_creds", locale), 116));
					}
				}
	
			} else if (settings.isLdapregisterrole()) 
				return registerLdapUser(locale, loginRequest, device, ip, response);
	
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_creds", locale), 102));
	
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	private ResponseEntity<?> registerLdapUser(Locale locale, LoginRequest loginRequest, String device, String ip, HttpServletResponse response) {

		try {

			ResponseEntity<?> ldapauthres = ldapService.authenticate(locale, loginRequest.getUsername(), loginRequest.getPassword());
			
			if (ldapauthres != null && ldapauthres.getBody() instanceof MessageResponse)
				return ldapauthres;
			
			if (ldapauthres.getBody() instanceof LdapResponse) {
		   	    Settings settings = settingsService.returndefaultSettings();
				
				LdapResponse ldapResponse = (LdapResponse) ldapauthres.getBody();
	
				Users user = new Users(ldapResponse.getFirstName(), ldapResponse.getLastName(), loginRequest.getUsername(), settings.getLdapdefaultrole(), ldapResponse.getEmail(), null, null, null);
	
				user = usersRepository.save(user);

				Roles role = rolesRepository.findByUserRole(settings.getLdapdefaultrole());
				
				if (role != null && !role.getRequire2fa()) {
					return authuser(device, ip, user, locale, response, role);
				}
	
				boolean sendsms = false;
				String expirydate = Utils.convertDateToString(jwtUtils.returnJwtExpriyDate(true), Constants.DATETIME_FORMAT);
				return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(Constants.VERIFY_OTP_TOKEN + loginRequest.getUsername(), null),
					user.getUser_id(), user.getUsername(), user.getEmail(), user.getUser_role(), sendsms, expirydate, getuserfullname(user, locale), getuserfullnamear(user, locale), role));
			}
	
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_creds", locale), 102));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> validateToken(Locale locale, String token, String username, String url) {

		try {
	        if (username == null || token == null) 
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_params", locale), 101));
	        
			if (jwtUtils.validateJwtToken(token, Constants.ACCESS_TOKEN + username)) {
	
				List<Tokens> tokens = tokensService.findByAccesstokenAndUsername(token, username);
				if (tokens != null && tokens.size() > 0)
					for (Tokens t : tokens) {
						if (t.getRevoked_date_time() != null)
							return ResponseEntity.ok(new MessageResponse(messageService.getMessage("revoked_token", locale), 444));
					}
				
				Optional<Users> userEntity = findByUsername(username);
				if (!userEntity.isPresent())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
	
				Users user = userEntity.get();
				if (!user.isEnable())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_creds", locale), 119));
				
				if (user.isLocked())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("account_locked", locale), 116));
				
				List<Authorization> authorizedapis = authorizationRepository.findByEnabledUserRole(user.getUser_role());
	
				if (url != null && !Utils.isapiauthorized(url, null, authorizedapis)) 
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized", locale).replace("#API#", url), 401));
	
				Roles role = roleRepository.findByUserRole(user.getUser_role());
				user.linkrole(role);
				user.setAuthorizedapis(authorizedapis);
				return ResponseEntity.ok(user);
			}
	
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_token", locale), 104));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	public Optional<Users> findByUsername(String username) {
		return usersRepository.findByUsername(username);
	}
 
	@Override
	public ResponseEntity<?> sendOTP(Locale locale, String token, String username, String type) {

		try {
			if (!jwtUtils.validateJwtToken(token, Constants.VERIFY_OTP_TOKEN + username)) 
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_token", locale), 105));
	
			Optional<Users> userEntity = findByUsername(username);
			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 106));
	
			Users user = userEntity.get();
	
			String msgBody = messageService.getMessage("otp_msg_body", locale);
			String otp = Utils.generate6Digits();
			msgBody = msgBody.replace("#USERNAME#", user.getFirst_name());
			msgBody = msgBody.replace("#OTP#", otp);
			user.setOtp(otp);
			usersRepository.save(user);
	
			if(type.equals(OTPTypeEnum.MAIL.name())) {
				String subject = messageService.getMessage("otp_subject", locale);
				EmailDetailsRq rq = new EmailDetailsRq(user.getEmail(), msgBody, subject);
				boolean sent = emailService.sendSimpleMail(rq);
				
				if (!sent)
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("mail_error", locale), 107));
				
			} else if (type.equals(OTPTypeEnum.SMS.name())) {
				
				if (user.getMobile_no() == null || user.getMobile_no().isEmpty())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("empty_phone_nbre", locale), 108));
	
				SMSDetailsRq rq = new SMSDetailsRq(user.getMobile_no(), msgBody);
				ResponseEntity<?> sendsms = smsService.sendSMS(locale, rq);
				if (sendsms != null)
					return sendsms;
			} else
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_params", locale), 109));
	
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("code_sent", locale)));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> verifyOTP(Locale locale, String token, String username, String otp, String device, String ip, HttpServletResponse response) {

		try {
			if (!jwtUtils.validateJwtToken(token, Constants.VERIFY_OTP_TOKEN + username))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_token", locale), 111));
				
			Optional<Users> userEntity = findByUsername(username);
			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 112));
			
			Users user = userEntity.get();
			if (user.getOtp() == null)
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("resend_code", locale), 113));
			
			if (!user.getOtp().equals(otp))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_code", locale), 114));
			
			user.setOtp(null);
			usersRepository.save(user);

			Roles role = rolesRepository.findByUserRole(user.getUser_role());
			return authuser(device, ip, user, locale, response, role);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}
	
	private ResponseEntity<?> authuser(String device, String ip, Users user, Locale locale, HttpServletResponse response, Roles role) {

		String key =  Base64.getEncoder().encodeToString(user.getUsername().getBytes());
		String refreshtoken = jwtUtils.generateJwtToken(Constants.REFRESH_TOKEN + user.getUsername(), false);
		String accesstoken = jwtUtils.generateJwtToken(Constants.ACCESS_TOKEN + user.getUsername(), true);

		Date refexpriydate = jwtUtils.returnJwtExpriyDate(false);
		Date accexpriydate = jwtUtils.returnJwtExpriyDate(true);
		String accessexpirydate = Utils.convertDateToString(accexpriydate, Constants.DATETIME_FORMAT);
		String refreshexpirydate = Utils.convertDateToString(refexpriydate, Constants.DATETIME_FORMAT);
		String id = generateUUID();
		tokensService.save(new Tokens(id, user.getUsername(), accesstoken, refreshtoken, device, ip, accexpriydate, null, refexpriydate, key));
		
		return ResponseEntity.ok(new JwtResponse(accesstoken, user.getUser_id(), user.getUsername(), user.getEmail(), user.getUser_role(), refreshtoken, key, accessexpirydate, refreshexpirydate, getuserfullname(user, locale), getuserfullnamear(user, locale), role));
	}

	private String generateUUID() {

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        Tokens token = tokensService.findById(uuidString);
        if (token == null) // if not found return it
        	return uuidString;
        
        return generateUUID();
	}

	@Override
	public ResponseEntity<?> refresh(Locale locale, String token, String refreshkey) {

		try {
			String username = new String(Base64.getDecoder().decode(refreshkey));
	
			List<Tokens> tokens = tokensService.findByRefreshtokenAndUsername(token, username);
			if (tokens != null && tokens.size() > 0)
				for (Tokens t : tokens) {
					if (t.getRevoked_date_time() != null)
						return ResponseEntity.ok(new MessageResponse(messageService.getMessage("revoked_token", locale), 110));
				}
			
			if (!jwtUtils.validateJwtToken(token, Constants.REFRESH_TOKEN + username))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_token", locale), 111));
				
			Optional<Users> userEntity = findByUsername(username);
			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 112));
	
			Users user = userEntity.get();
			String key =  Base64.getEncoder().encodeToString(username.getBytes());
			String refreshtoken = jwtUtils.generateJwtToken(Constants.REFRESH_TOKEN + username, false);
			String accesstoken = jwtUtils.generateJwtToken(Constants.ACCESS_TOKEN + username, true);
	
			Date accexpriydate = jwtUtils.returnJwtExpriyDate(true);
			Date refexpriydate = jwtUtils.returnJwtExpriyDate(false);
			String accessexpirydate = Utils.convertDateToString(accexpriydate, Constants.DATETIME_FORMAT);
			String refreshexpirydate = Utils.convertDateToString(refexpriydate, Constants.DATETIME_FORMAT);
			if (tokens != null && tokens.size() > 0)
				for (Tokens t : tokens) {
					t.setDate_time(new Date());
					t.setAccesstoken(accesstoken);
					t.setRefreshtoken(refreshtoken);
					t.setAccess_expiry_date(accexpriydate);
					t.setRefresh_expiry_date(refexpriydate);
					tokensService.save(t);
				}
			Roles role = rolesRepository.findByUserRole(user.getUser_role());
			return ResponseEntity.ok(new JwtResponse(accesstoken, user.getUser_id(), user.getUsername(), user.getEmail(), user.getUser_role(), refreshtoken, key, accessexpirydate, refreshexpirydate, getuserfullname(user, locale), getuserfullnamear(user, locale), role));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> revoke(Locale locale, String token, String refreshkey, String reason) {

		return tokensService.revoke(locale, token, refreshkey, reason);
	}

	@Override
	public ResponseEntity<?> usermenu(Locale locale, String username) {

		try {
			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			Users user = userEntity.get();
	 
			List<MenuRole> menurolelist = menuRoleRepository.findByUserRoleNoParent(user.getUser_role(), locale.getLanguage());
			
			List<MenuResponse> response = fillchildmenu(new ArrayList<MenuResponse>(), menurolelist, user.getUser_role(), locale);
			return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}

	}


	@Override
	public List<MenuResponse> fillchildmenu(List<MenuResponse> parent, List<MenuRole> menurolelist, String userrole, Locale locale) {

		if (menurolelist != null)
			for (MenuRole menurole : menurolelist) {
				
				MenuResponse menuResponse = new MenuResponse(menurole);
				
				List<MenuResponse> submenu = new ArrayList<MenuResponse>();
				List<MenuRole> childmenulist = menuRoleRepository.findByUserRoleAndParentId(userrole, menurole.getMenu().getId(), locale.getLanguage());
	
				for (MenuRole childmenu : childmenulist) {
					MenuResponse submenuResponse = new MenuResponse(childmenu);
	
					List<MenuRole> subchildmenulist = menuRoleRepository.findByUserRoleAndParentId(userrole, childmenu.getMenu().getId(), locale.getLanguage());
	
					submenuResponse.setSubmenu(fillchildmenu(new ArrayList<MenuResponse>(), subchildmenulist, userrole, locale));
					
					submenu.add(submenuResponse);
				}
				
				menuResponse.setSubmenu(submenu);
				parent.add(menuResponse);
			}
		
		return parent; 
	}

	@Override
	public ResponseEntity<?> verifyusername(Locale locale, String username, boolean isforgotpassword) {

		Optional<Users> userEntity = findByUsername(username);
   	    Settings settings = settingsService.returndefaultSettings();

		if (!userEntity.isPresent()) {
			return settings.isLdapregisterrole() ?
					ResponseEntity.ok(new VerifyUsernameResponse(false, AuthTypeEnum.LDAP.name(), null, settings.isPassregisterrole())) :
					ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
		}
		
		Users user = userEntity.get();

		Roles role = rolesRepository.findByUserRole(user.getUser_role());
		if (role == null)
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 121));
		
		if (isforgotpassword && !Utils.isrequiredpass(role.getAuth_type())) 
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 121));

   	    if (role.getAuth_type().equals(AuthTypeEnum.UAEPASS.name()) &&
    		!settings.isUaepassregisterrole())
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 129));
   	    
		String uaepasscallbackurl = null;
		if (role.getAuth_type().equals(AuthTypeEnum.UAEPASS.name()))
			uaepasscallbackurl = getuaepasscallbackurl(user.getUsername());
		boolean sendsms = user.getMobile_no() == null || user.getMobile_no().isEmpty() ? false : true;

		return ResponseEntity.ok(new VerifyUsernameResponse(sendsms, role.getAuth_type(), uaepasscallbackurl, settings.isPassregisterrole()));
		
	}
	
	private String getuaepasscallbackurl(String username) {

        String encodedusername = Base64.getEncoder().encodeToString(username.getBytes());
   	    Settings settings = settingsService.returndefaultSettings();
		return settings.getUaepassendpoint() + settings.getUaepassauthurl().replace("#CLIENTID#", settings.getUaepassusername()).replace("#STATE#", settings.getUaepassstate()).replace("#REDIRECTURL#", settings.getUaepassspsaredirecturl().replace("#USERNAME#", "?eu=" + encodedusername));
	}

	@Override
	public ResponseEntity<?> forgotpass(Locale locale, String username, String type, String recaptchaToken) {

		try {

	   	    Settings settings = settingsService.returndefaultSettings();
			if (settings.isRecaptchavalidation() == true) {
				ResponseEntity<?> verifycaptcha = googleRecaptchaService.verifyRecaptcha(locale, recaptchaToken);
				if (verifycaptcha != null) 
					return verifycaptcha;
			}
			
			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			Users user = userEntity.get();
			
			ResponseEntity<?> checkifauthorized = checkifauthorized(locale, user, 121);
			if (checkifauthorized != null)
				return checkifauthorized;
			
			String msgBody = messageService.getMessage("changepass_msg_body", locale);
			String code = Utils.generate6Digits();
			msgBody = msgBody.replace("#USERNAME#", user.getFirst_name());
			msgBody = msgBody.replace("#CODE#", code);
			user.setChangepasswordcode(code);
			usersRepository.save(user);
	
			if(type.equals(OTPTypeEnum.MAIL.name())) {
				String subject = messageService.getMessage("reset_password_subject", locale);
				EmailDetailsRq rq = new EmailDetailsRq(user.getEmail(), msgBody, subject);
				boolean sent = emailService.sendSimpleMail(rq);
				
				if (!sent)
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("mail_error", locale), 123));
				
			} else if (type.equals(OTPTypeEnum.SMS.name())) {
				
				if (user.getMobile_no() == null || user.getMobile_no().isEmpty())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("empty_phone_nbre", locale), 124));
	
				SMSDetailsRq rq = new SMSDetailsRq(user.getMobile_no(), msgBody);
				ResponseEntity<?> sendsms = smsService.sendSMS(locale, rq);
				if (sendsms != null)
					return sendsms;
			} else
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_params", locale), 125));

			String changepasstoken = jwtUtils.generateJwtToken(Constants.CHANGEPASS_TOKEN + username, null);
			
			return ResponseEntity.ok(new TokenResponse(changepasstoken));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 126));
		}
	}

	@Override
	public ResponseEntity<?> changepass(Locale locale, String username, String code, String newpassword, String confirmpassword, String changepasstoken) {

		try {

			if (!jwtUtils.validateJwtToken(changepasstoken, Constants.CHANGEPASS_TOKEN + username))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_token", locale), 111));

			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			
			/* If on user role type = PASS can use this API */
			ResponseEntity<?> checkifauthorized = checkifauthorized(locale, user, 127);
			if (checkifauthorized != null)
				return checkifauthorized;
			
			if (!newpassword.equals(confirmpassword))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_params", locale), 128));
			
			if (!Utils.isPasswordComplex(newpassword))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("complex_password_required", locale), 140));
			
			if (user.getChangepasswordcode() == null)
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("resend_code", locale), 129));
			
			if (!user.getChangepasswordcode().equals(code))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_code", locale), 130));

			user.setInvalidattempts(0);
			user.setLocked(false);
			user.setChangepasswordcode(null);
			user.setPassword(encoder.encode(newpassword));
			usersRepository.save(user);

			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("changepass_success", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}
	
	private ResponseEntity<?> checkifauthorized(Locale locale, Users user, int errorcode) {

		Roles role = rolesRepository.findByUserRole(user.getUser_role());
		if (role == null || !Utils.isrequiredpass(role.getAuth_type())) 
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), errorcode));
		
		return null;
	}

	@Override
	public ResponseEntity<?> register(Locale locale, @Valid RegisterRequest request, boolean isadmin) {

		try {
	   	    Settings settings = settingsService.returndefaultSettings();
			if (!isadmin && settings.isRecaptchavalidation() == true) {
				ResponseEntity<?> verifycaptcha = googleRecaptchaService.verifyRecaptcha(locale, request.getCaptchaToken());
				if (verifycaptcha != null) 
					return verifycaptcha;
			}
			
			// check if username already exist
			Optional<Users> checkexistingusername = usersRepository.findByUsername(request.getUsername());
			if (checkexistingusername.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_already_exist", locale), 177));
			
			Users user = new Users(request.getFirst_name(), request.getLast_name(), request.getUsername(), isadmin ? request.getUser_role() : settings.getPassdefaultrole(), request.getEmail(),
									request.getMobile_no(), Utils.convertStringToDate(request.getHire_date(), Constants.DATETIME_FORMAT), request.getSalary(), encoder.encode(request.getPassword()), isadmin ? request.isBypass3rdpartyauth() : false, request.getLongitude(), request.getLattitude());
	
			user = usersRepository.save(user);
	
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("request_completed", locale)));

		} catch (Exception e) {
			System.out.println(">> Registration error, " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale) + " :: "+ e.getMessage(), 829));
		}
	}

	@Override
	public ResponseEntity<?> able(Locale locale, String username, boolean disable) {
	
		try {
			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			user.setEnable(!disable);
			usersRepository.save(user);
	
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("request_completed", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> userchangepass(Locale locale, String username, String oldpassword, String newpassword,
			String confirmpassword) {

		try {

			if (!newpassword.equals(confirmpassword))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_params", locale), 128));
			
			if (!Utils.isPasswordComplex(newpassword))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("complex_password_required", locale), 140));

			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			
			/* If on user role type = PASS can use this API */
			ResponseEntity<?> checkifauthorized = checkifauthorized(locale, user, 127);
			if (checkifauthorized != null)
				return checkifauthorized;
			
			if (!encoder.matches(oldpassword, user.getPassword()))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_creds", locale), 131));

			user.setChangepasswordcode(null);
			user.setPassword(encoder.encode(newpassword));
			usersRepository.save(user);

			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("changepass_success", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> adminchangepass(Locale locale, String username, String newpassword, String confirmpassword) {

		try {

			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			
			if (!newpassword.equals(confirmpassword))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_params", locale), 128));
			
			user.setChangepasswordcode(null);
			user.setPassword(encoder.encode(newpassword));
			usersRepository.save(user);

			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("changepass_success", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> authorization(String username, Locale locale, String api, String menuauthid) {

		try {
			Optional<Users> userEntity = findByUsername(username);
	
			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			
			List<Authorization> returnAuthorizedapis = new ArrayList<Authorization>();
			if (api != null) {
				if (menuauthid != null) 
					returnAuthorizedapis = authorizationRepository.findByEnabledUserRoleAndLikeApiAndMenuauthid(user.getUser_role(), api, menuauthid);
				else
					returnAuthorizedapis = authorizationRepository.findByEnabledUserRoleAndLikeApi(user.getUser_role(), api);
			}
			else {
				if (menuauthid != null) 
					returnAuthorizedapis = authorizationRepository.findByEnabledUserRoleAndMenuauthid(user.getUser_role(), menuauthid);
				else
					returnAuthorizedapis = authorizationRepository.findByEnabledUserRole(user.getUser_role());
			}
			
			return ResponseEntity.ok(returnAuthorizedapis);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> details(Locale locale, String username) {
		try {
			Optional<Users> userEntity = findByUsername(username);
	
			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			
			Roles role = rolesRepository.findByUserRole(user.getUser_role());

			ResponseEntity<?> checkifauthorized = checkifauthorized(locale, user, 127);
			if (checkifauthorized == null)
				return ResponseEntity.ok(new UserResponse(user, false, true, role));
				
			return ResponseEntity.ok(new UserResponse(user, false, false, role));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> update(Locale locale, String username, @Valid UpdateUserReq request, boolean isadmin) {

		try {
			Optional<Users> userEntity = findByUsername(isadmin ? request.getUsername() : username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			user = user.updateUser(request.getFirst_name(), request.getLast_name(), Utils.convertStringToDate(request.getHire_date(), Constants.DATE_FORMAT), request.getSalary(), request.getFirst_name_ar(), request.getLast_name_ar(), request.getLongitude(), request.getLattitude());
			
			if (isadmin) {
				// Check if requested username changes the same in the database
				if (!user.getUsername().equals(request.getNewusername())) {
					Optional<Users> checkexistingusername = usersRepository.findByUsername(request.getNewusername());
					if (checkexistingusername.isPresent())
						return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_already_exist", locale), 177));
				}
				Roles role = rolesRepository.findByUserRole(request.getUser_role());
				if (role == null)
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_params", locale), 210));
					
				if (!Utils.getRequiredPassAuthType().contains(role.getAuth_type()))
					request.setPassword(null); // remove the password because it will not be used
				if (request.getPassword() == null || request.getPassword().equals(""))
					request.setPassword(user.getPassword()); // do not change the password
				else if (request.getPassword().trim().equals("") || !Utils.isPasswordComplex(request.getPassword()))
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("complex_password_required", locale), 140));
				else
					request.setPassword(encoder.encode(request.getPassword())); // update password
				
				user = user.adminUpdateUser(request.getFirst_name(), request.getLast_name(), request.getNewusername(), request.getUser_role(), request.getEmail(),
									request.getMobile_no(), Utils.convertStringToDate(request.getHire_date(), Constants.DATE_FORMAT), request.getSalary(), request.getPassword(), request.isBypass3rdpartyauth());
			}
			
			user = usersRepository.save(user);

			Roles role = rolesRepository.findByUserRole(user.getUser_role());

			return ResponseEntity.ok(new UserResponse(user, false, false, role));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
		
	}

	@Override
	public ResponseEntity<?> listcount(Locale locale) {
		
		try {
			long count = usersRepository.count();
			return ResponseEntity.ok(new CountResponse(count));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> userlist(Locale locale, int page, int size, String search, String sortcolumn, boolean descending, int draw) {

		try {
			Page<Users> userspage = null;
			long totalrows = usersRepository.count();
			long recordsFiltered = totalrows;

			Specification<Users> spec = JPASpecification.returnUserSpecification(search, sortcolumn, descending);
		    Pageable pageable = PageRequest.of(page, size);
		    userspage = usersRepository.findAll(spec, pageable);
		    
			if (search != null && !search.trim().equals("")) {
				List<Users> allusersbysearch = usersRepository.findAll(spec);
				recordsFiltered = allusersbysearch.size();
			} 
	
	        List<Users> list = new ArrayList<Users>(userspage.getContent());
	        
	        List<UserResponse> usersresponselist = new ArrayList<UserResponse>();
	        for (Users users : list) {
				ResponseEntity<?> checkifauthorized = checkifauthorized(locale, users, 127);
				Roles role = rolesRepository.findByUserRole(users.getUser_role());
				
				if (checkifauthorized == null)
		        	usersresponselist.add(new UserResponse(users, false, true, role));
				else
		        	usersresponselist.add(new UserResponse(users, false, false, role));
	        }
	        
	        DatatableResponse<UserResponse> datatableresponse = new DatatableResponse<UserResponse>(draw, totalrows, recordsFiltered, usersresponselist);
		       
			return ResponseEntity.ok(datatableresponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

    public static Specification<Users> withSearchTerm(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            String searchPattern = "%" + search + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get("first_name"), searchPattern),
                criteriaBuilder.like(root.get("last_name"), searchPattern),
                criteriaBuilder.like(root.get("email"), searchPattern),
                criteriaBuilder.like(root.get("mobile_no"), searchPattern),
                criteriaBuilder.like(root.get("first_name_ar"), searchPattern),
                criteriaBuilder.like(root.get("last_name_ar"), searchPattern),
                criteriaBuilder.like(root.get("user_role"), searchPattern)
            );
        };
    }

	@Override
	public ResponseEntity<?> verifychangeemail(Locale locale, String username, String newemail) {

		try {
			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			String msgBody = messageService.getMessage("changeemail_msg_body", locale);
			String code = Utils.generate6Digits();
			msgBody = msgBody.replace("#USERNAME#", user.getFirst_name());
			msgBody = msgBody.replace("#CODE#", code);
			user.setChangeemailcode(newemail + Constants.SEPARATOR + code);
			usersRepository.save(user);
			
			String subject = messageService.getMessage("change_email_subject", locale);
			EmailDetailsRq rq = new EmailDetailsRq(newemail, msgBody, subject);
			boolean sent = emailService.sendSimpleMail(rq);
			
			if (!sent)
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("mail_error", locale), 123));

			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("code_sent", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> changeemail(Locale locale, String username, String code, String newemail) {

		try {
			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));

			Users user = userEntity.get();
			String verifyemailandcode = newemail + Constants.SEPARATOR + code;
			if (user.getChangeemailcode() == null || !verifyemailandcode.equals(user.getChangeemailcode()))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_code", locale), 114));
				
			user.setChangeemailcode(null);
			user.setEmail(newemail);
			usersRepository.save(user);

			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("operation_success", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> verifychangemobile(Locale locale, String username, String newmobile) {

		try {
			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			String msgBody = messageService.getMessage("changemobile_msg_body", locale);
			String code = Utils.generate6Digits();
			msgBody = msgBody.replace("#USERNAME#", user.getFirst_name());
			msgBody = msgBody.replace("#CODE#", code);
			user.setChangemobilecode(newmobile + Constants.SEPARATOR + code);
			usersRepository.save(user);

			SMSDetailsRq rq = new SMSDetailsRq(newmobile, msgBody);
			ResponseEntity<?> sendsms = smsService.sendSMS(locale, rq);
			if (sendsms != null)
				return sendsms;
			
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("code_sent", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> changemobile(Locale locale, String username, String code, String newmobile) {

		try {
			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));

			Users user = userEntity.get();
			String verifyemailandcode = newmobile + Constants.SEPARATOR + code;
			if (user.getChangemobilecode() == null || !verifyemailandcode.equals(user.getChangemobilecode()))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_code", locale), 114));
				
			user.setChangeemailcode(null);
			user.setMobile_no(newmobile);
			usersRepository.save(user);

			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("operation_success", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> uaepass(Locale locale, String version, String code, String device, String ip, String encodedusername, HttpServletResponse httpresponse) {

		try {
	   	    Settings settings = settingsService.returndefaultSettings();
	   	    if (!settings.isUaepassregisterrole())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 121));
	   	    
			// Retrieve Emirates Id
			String uaepassaccessurl = settings.getUaepassendpoint() + settings.getUaepasscallbackurl().replace("#CALLBACKURL#", settings.getUaepassspsaredirecturl().replace("#USERNAME#", "?eu=" + encodedusername)).replace("#CODE#", code);
			UAEPassUserInfo uaePassUserInfo = new UAEPassUserInfo(settings.getUaepassendpoint() + settings.getUaepassuserinfourl(), uaepassaccessurl, settings.getUaepassusername(), settings.getUaepasspassword());
			String uaePassUserInfoRes = uaePassUserInfo.callAsGet();
			if (uaePassUserInfoRes == null)
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("auth_failed", locale), 191));
			JSONObject response = new JSONObject(uaePassUserInfoRes);
			if (response.has("error")) {
	//			String error = response.getString("error");
				String error_description = "Error while calling UAEPassUserInfo";
				if (response.has("error_description"))
					error_description = response.getString("error_description");
				return ResponseEntity.ok(new MessageResponse(error_description, 190));
			}
	
			if (encodedusername == null || encodedusername.trim().equals("")) encodedusername = null;
			Users user = null;
			String username = null;
			if (encodedusername != null) { // Admin added a new UAEPASS user
		        byte[] decodedBytes = Base64.getDecoder().decode(encodedusername);
		        String decodedusername = new String(decodedBytes);
				Optional<Users> userEntityeu = findByUsername(decodedusername);
				if (userEntityeu.isPresent()) {
					user = userEntityeu.get();
					username = decodedusername;
				}
			}
			
			if (user == null) {
				// Handling case when user registered using uuid, but later he got idn
				if (settings.isUaepassuseeid()) {
					if (!response.has("idn"))
						return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_eid", locale), 191));
					
					String emiratedid = response.getString("idn"); //username = emiratedid
					username = emiratedid;
					
					if (response.has("uuid")) {
						String uuid = response.getString("uuid"); 
						Optional<Users> userEntityuuid = findByUsername(uuid);
						if (userEntityuuid.isPresent()) { // change existing user with uuid to idn
							user = userEntityuuid.get();
							user.setUsername(username);
							user = usersRepository.save(user);
						}
					}
					if (user == null) {
						Optional<Users> userEntityidn = findByUsername(username);
						if (userEntityidn.isPresent()) 
							user = userEntityidn.get();
					}
					
				} 
				else { // by default use uuid
					if (!response.has("uuid"))
						return ResponseEntity.ok(new MessageResponse(messageService.getMessage("auth_failed", locale), 191));
					
					String uuid = response.getString("uuid"); //username = uuid
					username = uuid;
					
					if (response.has("idn")) {
						String idn = response.getString("idn"); 
						Optional<Users> userEntityidn = findByUsername(idn);
						if (userEntityidn.isPresent()) { // change existing user with idn to uuid
							user = userEntityidn.get();
							user.setUsername(username);
							user = usersRepository.save(user);
						}
					}
					if (user == null) {
						Optional<Users> userEntityidn = findByUsername(username);
						if (userEntityidn.isPresent()) 
							user = userEntityidn.get();
					}
				}
			}

			if (user != null) { // if exists
				
				if (!user.isEnable())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_creds", locale), 119));
				
				if (user.isLocked())
					return ResponseEntity.ok(new MessageResponse(messageService.getMessage("account_locked", locale), 116));
					
				boolean sendsms = user.getMobile_no() == null || user.getMobile_no().isEmpty() ? false : true;
	
				Roles role = rolesRepository.findByUserRole(user.getUser_role());
	
				String expirydate = Utils.convertDateToString(jwtUtils.returnJwtExpriyDate(true), Constants.DATETIME_FORMAT);
					
				if (!role.getRequire2fa()) {
					return authuser(device, ip, user, locale, httpresponse, role); 
				}
	
				return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(Constants.VERIFY_OTP_TOKEN + username, null),
					user.getUser_id(), user.getUsername(), user.getEmail(), user.getUser_role(), sendsms, expirydate, getuserfullname(user, locale), getuserfullnamear(user, locale), role));
	
			} else if (settings.isUaepassregisterrole()) {
				
				String firstnameEN = null, lastnameEN = null, email = null, mobile = null;
				if (response.has("firstnameEN"))
					firstnameEN = response.getString("firstnameEN");
				if (response.has("lastnameEN"))
					lastnameEN = response.getString("lastnameEN");
				if (response.has("email"))
					email = response.getString("email");
				if (response.has("mobile"))
					mobile = response.getString("mobile");
				
				Users newuser = new Users(firstnameEN, lastnameEN, username, settings.getUaepassdefaultrole(), email, mobile, null, null);
	
				newuser = usersRepository.save(newuser);
	
				Roles role = rolesRepository.findByUserRole(settings.getUaepassdefaultrole());
				
				if (role != null && !role.getRequire2fa()) {
					return authuser(device, ip, newuser, locale, httpresponse, role);
				}
	
				boolean sendsms = false;
				String expirydate = Utils.convertDateToString(jwtUtils.returnJwtExpriyDate(true), Constants.DATETIME_FORMAT);
				return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(Constants.VERIFY_OTP_TOKEN + username, null),
						newuser.getUser_id(), newuser.getUsername(), newuser.getEmail(), newuser.getUser_role(), sendsms, expirydate, getuserfullname(user, locale), getuserfullnamear(user, locale), role));
			}
	
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	private String getuserfullname(Users user, Locale locale) {

		String fullname = (user.getFirst_name() != null ? user.getFirst_name() : "") + (user.getLast_name() != null ? (" " + user.getLast_name()) : "");
		
		if (fullname.trim().equals(""))
			fullname = user.getUsername();
		
		return fullname;
	}
	private String getuserfullnamear(Users user, Locale locale) {

		String fullname = (user.getFirst_name_ar() != null ? user.getFirst_name_ar() : "") + (user.getLast_name_ar() != null ? (" " + user.getLast_name_ar()) : "");
		
		if (fullname.trim().equals(""))
			fullname = user.getUsername();
		
		return fullname;
	}

	@Override
	public ResponseEntity<?> rolelist(Locale locale, boolean all, int page, int size, String search, String sortcolumn, boolean descending, int draw) {
		try {
			if (all) {
				List<Roles> rolelist  = roleRepository.findAll();
				return ResponseEntity.ok(rolelist);
			}

			Page<Roles> userspage = null;
			long totalrows = roleRepository.count();
			long recordsFiltered = totalrows;

			Specification<Roles> spec = JPASpecification.returnRoleSpecification(search, sortcolumn, descending);
		    Pageable pageable = PageRequest.of(page, size);
		    userspage = roleRepository.findAll(spec, pageable);
		    
			if (search != null && !search.trim().equals("")) {
				List<Roles> allusersbysearch = roleRepository.findAll(spec);
				recordsFiltered = allusersbysearch.size();
			} 
	
	        List<Roles> list = new ArrayList<Roles>(userspage.getContent());
	        
	        DatatableResponse<Roles> datatableresponse = new DatatableResponse<Roles>(draw, totalrows, recordsFiltered, list);
		       
			return ResponseEntity.ok(datatableresponse);
	
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public List<Users> findByUserrole(String userrole) {
		return usersRepository.findByUserrole(userrole);
	}

	@Override
	public ResponseEntity<?> able(Locale locale, String username, String removeusername, boolean enable) {

		try {
			Optional<Users> userEntity = findByUsername(removeusername);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			user.setEnable(enable);
			usersRepository.save(user);
			
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("operation_success", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> registerusers(Locale locale, String version) {

		try {
	   	    Settings settings = settingsService.returndefaultSettings();
	   	    if (!settings.isUaepassregisterrole())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 121));

			String uaepasscallbackurl = null;
			uaepasscallbackurl = getuaepasscallbackurl("");
			return ResponseEntity.ok(new VerifyUsernameResponse(false, AuthTypeEnum.UAEPASS.name(), uaepasscallbackurl, settings.isPassregisterrole()));
	   	    
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> userbypassldap(Locale locale, String username, String selectedusername,
			String bypasspassword, boolean bypass3dparty) {

		try {
			Optional<Users> userEntity = findByUsername(selectedusername);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();

			Roles role = rolesRepository.findByUserRole(user.getUser_role());
			if (role == null || !role.getAuth_type().equalsIgnoreCase(AuthTypeEnum.LDAP.name())) 
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 144));
			
			if (bypass3dparty && !Utils.isPasswordComplex(bypasspassword))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("complex_password_required", locale), 140));

			user.setInvalidattempts(0);
			user.setLocked(false);
			user.setChangepasswordcode(null);
			
			user.setBypass3rdpartyauth(bypass3dparty);
			user.setPassword(bypass3dparty ? encoder.encode(bypasspassword) : null);
			usersRepository.save(user);

			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("changepass_success", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> rolegoalsaccesslist(Locale locale, String username, String pagename, boolean byteam) {

		try {
			Optional<Users> userEntity = findByUsername(username);

			if (!userEntity.isPresent())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 103));
			
			Users user = userEntity.get();
			Roles role = roleRepository.findByUserRole(user.getUser_role());
			if (role == null) 
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 144));

			
			if (byteam) {
				List<TeamsRolesUsersResponse> teamsrolesusersresp = new ArrayList<TeamsRolesUsersResponse>();
				List<String> teams = roleRepository.finddistinctteams(user.getUser_role());
				
				for (String team : teams) {
					Teams t = new Teams(Constants.NO_TEAM, Constants.NO_TEAM, Constants.NO_TEAM);
					if (team != null) {
						Optional<Teams> topt = teamsRepository.findById(team);
						if (topt.isPresent()) 
							t = topt.get();
					}
						
					List<Roles> roles = roleRepository.findByParentroleAndTeam(user.getUser_role(), team);
					List<RolesUsersResponse> rolesUsersResponses = new ArrayList<RolesUsersResponse>();
					if (roles != null && roles.size() > 0)
						for (Roles r : roles) {
							List<Users> userslist = usersRepository.findByUserrole(r.getUserRole());
							RolesUsersResponse rur = new RolesUsersResponse(r, userslist);
							rolesUsersResponses.add(rur);
						}
					TeamsRolesUsersResponse teamsRolesUsersResponse =  new TeamsRolesUsersResponse(t, rolesUsersResponses);
					teamsrolesusersresp.add(teamsRolesUsersResponse);
				}
				return ResponseEntity.ok(teamsrolesusersresp);
			}
			

			List<Roles> roles = roleRepository.findByParentrole(user.getUser_role());
				
			List<RolesUsersResponse> rolesusersresponses = new ArrayList<RolesUsersResponse>();
			if (roles != null && roles.size() > 0)
				for (Roles r : roles) {
					List<Users> userslist = usersRepository.findByUserrole(r.getUserRole());
					RolesUsersResponse rur = new RolesUsersResponse(r, userslist);
					rolesusersresponses.add(rur);
				}
			return ResponseEntity.ok(rolesusersresponses);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> teamlist(Locale locale, Boolean all, Integer page, Integer size, String search,
			String sortcolumn, Boolean descending, Integer draw) {
		try {
			if (all) 
				return ResponseEntity.ok(teamsRepository.findAll());

			Page<Teams> userspage = null;
			long totalrows = teamsRepository.count();
			long recordsFiltered = totalrows;

			Specification<Teams> spec = JPASpecification.returnTeamSpecification(search, sortcolumn, descending);
		    Pageable pageable = PageRequest.of(page, size);
		    userspage = teamsRepository.findAll(spec, pageable);
		    
			if (search != null && !search.trim().equals("")) {
				List<Teams> allusersbysearch = teamsRepository.findAll(spec);
				recordsFiltered = allusersbysearch.size();
			} 
	
	        List<Teams> list = new ArrayList<Teams>(userspage.getContent());
	        
	        DatatableResponse<Teams> datatableresponse = new DatatableResponse<Teams>(draw, totalrows, recordsFiltered, list);
		       
			return ResponseEntity.ok(datatableresponse);
	
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> teamsave(Locale locale, String username, @Valid TeamRq req) {
		try {
			
			Teams team = new Teams(req);
			team = teamsRepository.save(team);
			return ResponseEntity.ok(team);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> teamremove(Locale locale, String username, String code) {
		try {
			
			List<Roles> roles = rolesRepository.findByTeam(code);
			if (roles != null && roles.size() > 0)
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("role_team_already_exist", locale), 121));
			
			teamsRepository.deleteById(code);
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("operation_success", locale)));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> teamrolelist(Locale locale, String code) {
		try {
	        List<TeamsRolesResponse> trlist = new ArrayList<TeamsRolesResponse>();

			if (code != null) {
				Optional<Teams> teamopt = teamsRepository.findById(code);
				if (teamopt.isPresent()) {
					Teams t = teamopt.get();

	        		List<Roles> roles = rolesRepository.findByTeam(code);
		        	TeamsRolesResponse trrs = new TeamsRolesResponse(t, roles);
		        	trlist.add(trrs);
				}
				return ResponseEntity.ok(trlist);
			}
			 
	        List<Teams> list = teamsRepository.findAll();
	        
	        if (list != null && list.size() > 0) {
	        	for (Teams teams : list) {

	        		List<Roles> roles = rolesRepository.findByTeam(teams.getCode());
		        	TeamsRolesResponse trrs = new TeamsRolesResponse(teams, roles);
		        	trlist.add(trrs);
	        	}
	        }
		       
			return ResponseEntity.ok(trlist);
	
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> teamroleuserslist(Locale locale, String serverkey, String serverpass, String team, String role) {

		try {
			if (!settingsService.returnServerkey().equals(serverkey) ||
					!settingsService.returnServerpass().equals(serverpass))
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
				
			if (team != null) {
				Optional<Teams> teamopt = teamsRepository.findById(team);
				if (teamopt.isPresent()) {
	        		List<Roles> roles = rolesRepository.findByTeam(team);
	        		for (Roles r : roles) {
	        			List<Users> ulist = usersRepository.findByUserrole(r.getUserRole());
	        			return ResponseEntity.ok(ulist);
	        		}
				}
			}

			else if (role != null) {
				Roles r = rolesRepository.findByUserRole(role);
				if (r != null) {
	    			List<Users> ulist = usersRepository.findByUserrole(role);
	    			return ResponseEntity.ok(ulist);
				}
			}

			return ResponseEntity.ok(new ArrayList<>());
	
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> childrolesuserslist(Locale locale, String serverkey, String serverpass,
			String parentrole) {
		try {
			List<Roles> childroles = rolesRepository.findByParentrole(parentrole);
			for (Roles r : childroles) {
				List<Users> ulist = usersRepository.findByUserrole(r.getUserRole());
				return ResponseEntity.ok(ulist);
			}
			return ResponseEntity.ok(new ArrayList<>());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

}
