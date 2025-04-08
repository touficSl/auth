package com.service.auth.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.auth.builder.request.RegisterRequest;
import com.service.auth.builder.request.UpdateUserReq;
import com.service.auth.config.Constants;
import com.service.auth.service.TokensService;
import com.service.auth.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokensService tokenService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									  @Valid @RequestBody RegisterRequest request) {

		return userService.register(locale, request, false);
	}

	
	@RequestMapping(value = "/authorization", method = RequestMethod.POST)
	public ResponseEntity<?> authorization(@RequestHeader(name = "username", required = true) String username,
										   @RequestHeader(name = "Accept-Language", required = false) Locale locale,
		    							   @RequestHeader(name = "api", required = false) String api,
		    							   @RequestHeader(name = "menuauthid", required = false) String menuauthid) {

		return userService.authorization(username, locale, api, menuauthid);
	}

	@RequestMapping(value = "/change/password", method = RequestMethod.POST)
	public ResponseEntity<?> userchangepass(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "oldpassword", required = true) String oldpassword,
									    @RequestHeader(name = "newpassword", required = true) String newpassword,
									    @RequestHeader(name = "confirmpassword", required = true) String confirmpassword) {

		return userService.userchangepass(locale, username, oldpassword, newpassword, confirmpassword);
	}
	
	@RequestMapping(value = "/menu/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
								  @RequestHeader(name = "username", required = true) String username) {

		return userService.usermenu(locale, username);
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public ResponseEntity<?> details(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
								  @RequestHeader(name = "username", required = true) String username) {

		return userService.details(locale, username);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
								  @RequestHeader(name = "username", required = true) String username,
								  @Valid @RequestBody UpdateUserReq req) {

		return userService.update(locale, username, req, false);
	}

	@RequestMapping(value = "/verify/change/email", method = RequestMethod.POST)
	public ResponseEntity<?> verifychangeemail(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "newemail", required = true) String newemail) {

		return userService.verifychangeemail(locale, username, newemail);
	}

	@RequestMapping(value = "/change/email", method = RequestMethod.POST)
	public ResponseEntity<?> changeemail(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "code", required = true) String code,
									    @RequestHeader(name = "newemail", required = true) String newemail) {

		return userService.changeemail(locale, username, code, newemail);
	}

	@RequestMapping(value = "/verify/change/mobile", method = RequestMethod.POST)
	public ResponseEntity<?> verifychangemobile(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "newmobile", required = true) String newmobile) {

		return userService.verifychangemobile(locale, username, newmobile);
	}

	@RequestMapping(value = "/change/mobile", method = RequestMethod.POST)
	public ResponseEntity<?> changemobile(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "code", required = true) String code,
									    @RequestHeader(name = "newmobile", required = true) String newmobile) {

		return userService.changemobile(locale, username, code, newmobile);
	}
	
	@RequestMapping(value = "/token/list", method = RequestMethod.POST)
	public ResponseEntity<?> tokenlist(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
								  @RequestHeader(name = "page", required = false, defaultValue = "0") Integer page,
								  @RequestHeader(name = "size", required = false, defaultValue = "0") Integer size,
								  @RequestHeader(name = "search", required = false) String search,
								  @RequestHeader(name = "sortcolumn", required = false) String sortcolumn,
								  @RequestHeader(name = "descending", required = false, defaultValue = "false") Boolean descending,
						          @RequestHeader(name = "draw", required = false, defaultValue = "1") Integer draw,
								  @RequestHeader(name = "username", required = true) String username) {
		
		return tokenService.list(locale, false, page, size, search, sortcolumn, descending, draw, username);
	}

	@RequestMapping(value = "/token/force/revoke", method = RequestMethod.POST)
	public ResponseEntity<?> tokenforcerevoke(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
										 @RequestHeader(name = "token", required = true) String token, 
										 @RequestHeader(name = "refreshtoken", required = true) String refreshtoken, 
										 @RequestHeader(name = "refreshkey", required = true) String refreshkey,
										 @RequestHeader(name = "username", required = true) String username) {

		return tokenService.revoke(locale, refreshtoken, refreshkey, Constants.FORCE_REVOKE);
	}
}
