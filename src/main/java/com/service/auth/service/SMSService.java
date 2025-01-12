package com.service.auth.service;

import java.util.Locale;

import org.springframework.http.ResponseEntity;

import com.service.auth.builder.request.SMSDetailsRq;

public interface SMSService {

	ResponseEntity<?> sendSMS(Locale locale, SMSDetailsRq details);

}
