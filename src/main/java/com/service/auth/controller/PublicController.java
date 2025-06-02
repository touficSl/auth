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
import com.service.auth.builder.response.MessageResponse;
import com.service.auth.config.Constants;
import com.service.auth.enumeration.OTPTypeEnum;
import com.service.auth.service.MessageService;
import com.service.auth.service.SettingsService;
import com.service.auth.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicController {

	@Autowired
	private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SettingsService settingsService;
    
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(
			@RequestHeader(name = "serverkey", required = true) String serverkey,
			@RequestHeader(name = "serverpass", required = true) String serverpass,
			@RequestHeader(name = "Accept-Language", required = false) Locale locale,
			@Valid @RequestBody RegisterRequest request) {

		// register user after payment
    	// call auth to register a user and send email to reset his password (use ps data)
		// allow user to set his account password to see his donations that he did on the projects
		// redirect user to otp change password page he enters the otp from his email and change his password

		if (!settingsService.returnServerkey().equals(serverkey) ||
				!settingsService.returnServerpass().equals(serverpass))
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));

		request.setPassword(Constants.COMPLEX_DEFAULT_PASS);
		request.setUser_role(Constants.DEFAULT_USER_ROLE);
		request.setBypass3rdpartyauth(false);
		userService.register(locale, request, true);
		return userService.forgotpass(locale, request.getUsername(), OTPTypeEnum.MAIL.name(), request.getCaptchaToken());
	}
}
