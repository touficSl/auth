package com.service.auth.service;


import java.util.List;
import java.util.Locale;

import org.springframework.http.ResponseEntity;

import com.service.auth.builder.request.LoginRequest;
import com.service.auth.builder.request.RegisterRequest;
import com.service.auth.builder.request.TeamRq;
import com.service.auth.builder.request.UpdateUserReq;
import com.service.auth.builder.response.MenuResponse;
import com.service.auth.model.MenuRole;
import com.service.auth.model.Users;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface UserService {

	ResponseEntity<?> authenticate(Locale locale, LoginRequest loginrequest, boolean verifyotptoken, String device, String ip, HttpServletResponse response);

	ResponseEntity<?> validateToken(Locale locale, String token, String username, String url);

	ResponseEntity<?> sendOTP(Locale locale, String token, String username, String type);

	ResponseEntity<?> verifyOTP(Locale locale, String token, String username, String otp, String device, String ip, HttpServletResponse response);

	ResponseEntity<?> refresh(Locale locale, String token, String key);

	ResponseEntity<?> revoke(Locale locale, String token, String key, String reason);

	ResponseEntity<?> usermenu(Locale locale, String username);
	
	ResponseEntity<?> verifyusername(Locale locale, String username, boolean isforgotpassword);

	ResponseEntity<?> forgotpass(Locale locale, String username, String type, String recaptchaToken);

	ResponseEntity<?> changepass(Locale locale, String username, String code, String newpassword,
			String confirmpassword, String changepasstoken);

	ResponseEntity<?> register(Locale locale, @Valid RegisterRequest request, boolean isadmin);

	ResponseEntity<?> able(Locale locale, String username, boolean disable);

	ResponseEntity<?> userchangepass(Locale locale, String username, String oldpassword, String newpassword,
			String confirmpassword);

	ResponseEntity<?> adminchangepass(Locale locale, String username, String newpassword, String confirmpassword);

	ResponseEntity<?> authorization(String username, Locale locale, String api, String menuauthid);

	ResponseEntity<?> details(Locale locale, String username);

	ResponseEntity<?> update(Locale locale, String username, @Valid UpdateUserReq req, boolean isadmin);

	ResponseEntity<?> listcount(Locale locale);

	ResponseEntity<?> userlist(Locale locale, int page, int size, String search, String sortcolumn, boolean descending, int draw);

	ResponseEntity<?> verifychangeemail(Locale locale, String username, String newemail);

	ResponseEntity<?> changeemail(Locale locale, String username, String code, String newemail);

	ResponseEntity<?> verifychangemobile(Locale locale, String username, String newmobile);

	ResponseEntity<?> changemobile(Locale locale, String username, String code, String newmobile);

	ResponseEntity<?> uaepass(Locale locale, String version, String code, String device, String ip, String encodedusername, HttpServletResponse response);

	ResponseEntity<?> rolelist(Locale locale, boolean all, int page, int size, String search, String sortcolumn, boolean descending, int draw);
	
	List<MenuResponse> fillchildmenu(List<MenuResponse> parent, List<MenuRole> menurolelist, String userrole, Locale locale);

	List<Users> findByUserrole(String userrole);

	ResponseEntity<?> able(Locale locale, String username, String removeusername, boolean enable);

	ResponseEntity<?> registerusers(Locale locale, String version);

	ResponseEntity<?> userbypassldap(Locale locale, String username, String selectedusername, String bypasspassword, boolean bypass3dparty);

	ResponseEntity<?> rolegoalsaccesslist(Locale locale, String username, String pagename, boolean byteam);

	ResponseEntity<?> teamlist(Locale locale, Boolean all, Integer page, Integer size, String search, String sortcolumn, Boolean descending, Integer draw);

	ResponseEntity<?> teamsave(Locale locale, String username, @Valid TeamRq req);

	ResponseEntity<?> teamremove(Locale locale, String username, String code);

	ResponseEntity<?> teamrolelist(Locale locale, String code);

	ResponseEntity<?> teamroleuserslist(Locale locale, String serverkey, String serverpass, String team, String role);

	ResponseEntity<?> childrolesuserslist(Locale locale, String serverkey, String serverpass, String parentrole);

}
