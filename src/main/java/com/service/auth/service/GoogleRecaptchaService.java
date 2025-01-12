package com.service.auth.service;

import java.util.Locale;

import org.springframework.http.ResponseEntity;

public interface GoogleRecaptchaService {

	ResponseEntity<?> verifyRecaptcha(Locale locale, String token);

}
