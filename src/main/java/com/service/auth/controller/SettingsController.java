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

import com.service.auth.builder.request.UpdateSettingsRq;
import com.service.auth.model.Settings;
import com.service.auth.service.SettingsService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/settings")
public class SettingsController {
	
	@Autowired
	private SettingsService settingsService;
	
	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public ResponseEntity<?> details(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

		Settings returnsettings = settingsService.returndefaultSettings();
		return ResponseEntity.ok(returnsettings.removeprivatedata());
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
			  						@Valid @RequestBody UpdateSettingsRq request) {

		return settingsService.update(locale, request);
	}

}
