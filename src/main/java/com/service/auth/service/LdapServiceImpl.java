package com.service.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.auth.builder.response.LdapResponse;
import com.service.auth.builder.response.MessageResponse;
import com.service.auth.config.LdapUser;
import com.service.auth.model.Settings;

import java.util.List;
import java.util.Locale;

@Service
public class LdapServiceImpl implements LdapService{

    @Autowired
    private SettingsService settingsService;
    
    @Autowired
    private MessageService messageService;
    

	public ResponseEntity<?> authenticate(Locale locale, String username, String password) {
		try {

	   	    Settings settings = settingsService.returndefaultSettings();
	        MyLdapService ldapService = new MyLdapService(settings.getLdapurl(), settings.getLdapbasedn(), username + settings.getLdapdomain(), password);

	        List<LdapUser> users = ldapService.searchUsers(username, settings.getLdapbasedn());

			if (!users.isEmpty()) {
				String firstName = users.get(0).getName();
				String lastName = users.get(0).getLastname();
				String email =users.get(0).getEmail();

				return ResponseEntity.ok(new LdapResponse(firstName, lastName, email));
			} else {
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 301));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("user_not_exist", locale), 302));
		}
	}
}
