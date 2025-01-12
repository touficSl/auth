package com.service.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.auth.model.Settings;

@Service
public class APIAuthServiceImpl implements APIAuthCheckService {

    @Autowired
    private SettingsService settingsService;

	@Override
	public boolean authenticate(String apikey, String apisecret) {

   	    Settings settings = settingsService.returndefaultSettings();
		if (settings.getApikey().equals(apikey) && 
				settings.getApisecret().equals(apisecret))
			return true;
		
		return false;
	}

}
