package com.service.auth.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.auth.builder.request.LoginRequest;
import com.service.auth.config.Constants;
import com.service.auth.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/token")
public class AuthController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = {"/generate", "/{version}/generate"}, method = RequestMethod.POST)
	public ResponseEntity<?> generate(HttpServletResponse response, 
									  @PathVariable(name = "version", required = false) String version,
									  @RequestHeader(name = "Accept-Language", required = false) Locale locale,
									  @Valid @RequestBody LoginRequest loginRequest,
									  @RequestHeader(name = "device", required = false) String device, 
									  @RequestHeader(name = "ip", required = false) String ip) {

		return userService.authenticate(locale, loginRequest, true, device, ip, response);
	}

	@RequestMapping(value = {"/verify", "/{version}/verify"}, method = RequestMethod.POST)
	public ResponseEntity<?> verify(@PathVariable(name = "version", required = false) String version,
									@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									@RequestHeader(name = "token", required = true) String token, 
									@RequestHeader(name = "username", required = true) String username) {

		return userService.validateToken(locale, token, username, null); // null because not secure if the URL is sent from the front end
	}

	@RequestMapping(value = {"/send/otp", "/{version}/send/otp"}, method = RequestMethod.POST)
	public ResponseEntity<?> sendOTP(@PathVariable(name = "version", required = false) String version,
									 @RequestHeader(name = "Accept-Language", required = false) Locale locale,
									 @RequestHeader(name = "token", required = true) String token, 
									 @RequestHeader(name = "username", required = true) String username, 
									 @RequestHeader(name = "type", required = true) String type) {

		return userService.sendOTP(locale, token, username, type);
	}

	@RequestMapping(value = {"/verify/otp", "/{version}/verify/otp"}, method = RequestMethod.POST)
	public ResponseEntity<?> verifyOTP(HttpServletResponse response,
									   @PathVariable(name = "version", required = false) String version,
									   @RequestHeader(name = "Accept-Language", required = false) Locale locale,
									   @RequestHeader(name = "token", required = true) String token, 
									   @RequestHeader(name = "username", required = true) String username, 
									   @RequestHeader(name = "otp", required = true) String otp, 
									   @RequestHeader(name = "device", required = false) String device, 
									   @RequestHeader(name = "ip", required = false) String ip) {
		
		return userService.verifyOTP(locale, token, username, otp, device, ip, response);
	}

	@RequestMapping(value = {"/refresh", "/{version}/refresh"}, method = RequestMethod.POST)
	public ResponseEntity<?> refresh(@PathVariable(name = "version", required = false) String version,
									 @RequestHeader(name = "Accept-Language", required = false) Locale locale,
									 @RequestHeader(name = "token", required = true) String token, 
									 @RequestHeader(name = "key", required = true) String key) {

		return userService.refresh(locale, token, key);
	}

	@RequestMapping(value = {"/revoke", "/{version}/revoke"}, method = RequestMethod.POST)
	public ResponseEntity<?> revoke(@PathVariable(name = "version", required = false) String version,
									@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									@RequestHeader(name = "token", required = true) String token, 
									@RequestHeader(name = "key", required = true) String key) {

		return userService.revoke(locale, token, key, Constants.LOGOUT);
	}


	@RequestMapping(value = {"/verify/username", "/{version}/verify/username"}, method = RequestMethod.POST)
	public ResponseEntity<?> verifyusername(@PathVariable(name = "version", required = false) String version,
											@RequestHeader(name = "Accept-Language", required = false) Locale locale,
											@RequestHeader(name = "username", required = true) String username,
											@RequestHeader(name = "isforgotpassword", required = false) boolean isforgotpassword) {

		return userService.verifyusername(locale, username, isforgotpassword);
	}

	@RequestMapping(value = {"/forgot/password", "/{version}/forgot/password"}, method = RequestMethod.POST)
	public ResponseEntity<?> forgotpass(@PathVariable(name = "version", required = false) String version,
										@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "recaptchaToken", required = true) String recaptchaToken,
									    @RequestHeader(name = "type", required = true) String type) {

		return userService.forgotpass(locale, username, type, recaptchaToken);
	}

	@RequestMapping(value = {"/change/password", "/{version}/change/password"}, method = RequestMethod.POST)
	public ResponseEntity<?> changepass(@PathVariable(name = "version", required = false) String version,
										@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "code", required = true) String code,
									    @RequestHeader(name = "newpassword", required = true) String newpassword,
									    @RequestHeader(name = "confirmpassword", required = true) String confirmpassword,
									    @RequestHeader(name = "token", required = true) String token) {

		return userService.changepass(locale, username, code, newpassword, confirmpassword, token);
	}
	

	@RequestMapping(value = {"/uaepass", "/{version}/uaepass"}, method = RequestMethod.POST, headers = "Accept=application/json") 
	public ResponseEntity<?> uaepass(HttpServletResponse response,
									 @PathVariable(name = "version", required = false) String version,
									 @RequestHeader(name = "Accept-Language", required = false) Locale locale,
									 @RequestHeader(name = "code", required = true) String code,
									 @RequestHeader(name = "device", required = false) String device, 
									 @RequestHeader(name = "ip", required = false) String ip,
									 @RequestHeader(name = "encodedusername", required = false) String encodedusername) { 

		return userService.uaepass(locale, version, code, device, ip, encodedusername, response);
	}
	


	@RequestMapping(value = {"/register/uaepass", "/{version}/register/uaepass"}, method = RequestMethod.POST, headers = "Accept=application/json") 
	public ResponseEntity<?> registeruaepass(@PathVariable(name = "version", required = false) String version,
									 		 @RequestHeader(name = "Accept-Language", required = false) Locale locale) { 

		return userService.registerusers(locale, version);
	}
}
