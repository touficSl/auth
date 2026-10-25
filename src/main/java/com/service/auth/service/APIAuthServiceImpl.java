package com.service.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.auth.config.Utils;
import com.service.auth.model.Settings;

@Service
public class APIAuthServiceImpl implements APIAuthCheckService {

    @Autowired
    private SettingsService settingsService;

	@Override
	public boolean authenticate(String apikey, String apisecret) {

   	    Settings settings = settingsService.returndefaultSettings();
		// constant time compare, and an empty configured key never matches
		if (Utils.secureEquals(settings.getApikey(), apikey) &
				Utils.secureEquals(settings.getApisecret(), apisecret))
			return true;
		
		return false;
	}

}
