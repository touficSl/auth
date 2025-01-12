package com.service.auth.service;


import java.util.Locale;

import org.springframework.http.ResponseEntity;

public interface LdapService {

	ResponseEntity<?> authenticate(Locale locale, String username, String password);

}
