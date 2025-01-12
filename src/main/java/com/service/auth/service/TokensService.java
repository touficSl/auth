package com.service.auth.service;


import java.util.List;
import java.util.Locale;

import org.springframework.http.ResponseEntity;

import com.service.auth.model.Tokens;

public interface TokensService {

	ResponseEntity<?> listcount(Locale locale);

	ResponseEntity<?> list(Locale locale, boolean all, int page, int size, String search, String sortcolumn, boolean descending, int draw, String username);
	
	ResponseEntity<?> revoke(Locale locale, String token, String key, String reason);

	List<Tokens> findByAccesstokenAndUsername(String token, String username);

	List<Tokens> findByRefreshtokenAndUsername(String token, String username);

	Tokens save(Tokens tokens);
	
	Tokens findById(String id);
}
