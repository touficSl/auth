package com.service.auth.service;

import java.util.Locale;

import org.springframework.http.ResponseEntity;

import com.service.auth.builder.request.UpdateSettingsRq;
import com.service.auth.model.Settings;

import jakarta.validation.Valid;

public interface SettingsService {

	Settings returndefaultSettings();

	ResponseEntity<?> authtypelist(Locale locale);

	ResponseEntity<?> update(Locale locale, @Valid UpdateSettingsRq request);

	String returnServerkey();
	String returnServerpass();

}
