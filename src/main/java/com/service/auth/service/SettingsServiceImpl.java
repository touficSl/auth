package com.service.auth.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.auth.builder.request.UpdateSettingsRq;
import com.service.auth.builder.response.MessageResponse;
import com.service.auth.config.Utils;
import com.service.auth.model.Settings;
import com.service.auth.repository.SettingsRepository;

import jakarta.validation.Valid;

@Service
public class SettingsServiceImpl implements SettingsService {

	@Value("${spring.ldap.url}") 
	private String ldapurl;
	@Value("${spring.ldap.basedn}") 
	private String ldapbasedn;
	@Value("${spring.ldap.domain}") 
	private String ldapdomain;
	@Value("${spring.ldap.register.role}") 
	private boolean ldapregisterrole;
	@Value("${spring.ldap.default.role}") 
	private String ldapdefaultrole;

	@Value("${spring.pass.default.role}") 
	private String passdefaultrole;
	@Value("${spring.pass.register.role}") 
	private boolean passregisterrole;

	@Value("${spring.app.jwtSecret}") 
	private String jwtsecret;
	@Value("${spring.app.jwtExpirationMs}") 
	private long jwtexpirationms;
	@Value("${spring.app.jwtExpirationMs.short}") 
	private long jwtexpirationmsshort;
	@Value("${spring.app.jwtExpirationMs.code}") 
	private long jwtexpirationmscode;

	@Value("${spring.apikey}") 
	private String apikey;
	@Value("${spring.apisecret}") 
	private String apisecret;
	@Value("${spring.admin.key}") 
	private String adminkey;
	
	@Value("${spring.recaptcha.validation}") 
	private boolean recaptchavalidation;
	@Value("${spring.recaptcha.siteKey}") 
	private String recaptchasitekey;
	@Value("${spring.recaptcha.api}") 
	private String recaptchaapi;

	@Value("${spring.mail.host}") 
	private String mailhost;
	@Value("${spring.mail.port}") 
	private int mailport;
	@Value("${spring.mail.username}") 
	private String mailusername;
	@Value("${spring.mail.password}") 
	private String mailpassword;
	@Value("${spring.mail.starttls.enable}") 
	private boolean mailstarttlsenable;
	@Value("${spring.mail.from}") 
	private String mailfrom;
	@Value("${spring.mail.auth}") 
	private boolean mailauth;
	@Value("${spring.mail.support}") 
	private String mailsupport;

	@Value("${spring.sms.auth.api}") 
	private String smsauthapi;
	@Value("${spring.sms.auth.username}") 
	private String smsauthusername;
	@Value("${spring.sms.auth.password}") 
	private String smsauthpassword;
	@Value("${spring.sms.api}") 
	private String smsapi;
	@Value("${spring.sms.applicationId}") 
	private String smsapplicationid;
	@Value("${spring.sms.password}") 
	private String smspassword;

	@Value("${spring.maximum.invalid.attempts}") 
	private int maximuminvalidattempts;
	

	@Value("${spring.uaepass.default.role}") 
	private String uaepassdefaultrole;
	@Value("${spring.uaepass.register.role}") 
	private boolean uaepassregisterrole;
	@Value("${spring.uaepass.username}") 
	private String uaepassusername;
	@Value("${spring.uaepass.password}") 
	private String uaepasspassword;
	@Value("${spring.uaepass.endpoint}") 
	private String uaepassendpoint;
	@Value("${spring.uaepass.callback.url}") 
	private String uaepasscallbackurl;
	@Value("${spring.uaepass.userinfo.url}") 
	private String uaepassuserinfourl;
	@Value("${spring.uaepass.state}") 
	private String uaepassstate;
	@Value("${spring.uaepass.redirect.url}") 
	private String uaepassspsaredirecturl;
	@Value("${spring.uaepass.auth.url}") 
	private String uaepassauthurl;
	@Value("${spring.uaepass.use.eid}") 
	private boolean uaepassuseeid;

	@Value("${spring.serverkey}") 
	private String serverkey;
	@Value("${spring.serverpass}") 
	private String serverpass;
	
	@Autowired
	private SettingsRepository settingsRepository;

    @Autowired
    private MessageService messageService;

	@Override
	public Settings returndefaultSettings() {
		List<Settings> settings = settingsRepository.findByIsdefault(true);
		
		if (settings == null || settings.size() == 0 || settings.get(0) == null) {
			Settings builtinsettings = new Settings(ldapregisterrole, ldapdefaultrole, ldapdomain, ldapurl,
					ldapbasedn, apikey, apisecret, adminkey,
					jwtsecret, jwtexpirationms, jwtexpirationmsshort, jwtexpirationmscode, uaepassregisterrole,
					uaepassdefaultrole, uaepassusername, uaepasspassword, uaepassendpoint,
					uaepasscallbackurl, uaepassuserinfourl, uaepassstate, uaepassspsaredirecturl,
					uaepassauthurl, uaepassuseeid, passregisterrole, passdefaultrole,
					maximuminvalidattempts, recaptchavalidation, recaptchasitekey, recaptchaapi,
					smsauthapi, smsauthusername, smsauthpassword, smsapi, smsapplicationid,
					smspassword, mailhost, mailport, mailusername, mailpassword,
					mailstarttlsenable, mailfrom, mailauth, mailsupport, true);
			return builtinsettings;
		}

		settings.get(0).setCanupdate(true);
		return settings.get(0);
	}

	@Override
	public ResponseEntity<?> authtypelist(Locale locale) {
		return ResponseEntity.ok(Utils.getAuthTypeResponse());
	}


	@Override
	public ResponseEntity<?> update(Locale locale, @Valid UpdateSettingsRq request) {
		List<Settings> settings = settingsRepository.findByIsdefault(true);

		if (settings == null || settings.size() == 0 || settings.get(0) == null)
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 511));
			
		Settings s = settings.get(0);
		s.updatesettings(request);
		
		s = settingsRepository.save(s);
		
		return ResponseEntity.ok(s.removeprivatedata());
	}

	@Override
	public String returnServerkey() {
		return serverkey;
	}

	@Override
	public String returnServerpass() {
		return serverpass;
	}
}
