package com.service.auth.service;

import java.util.Locale;

import org.springframework.http.ResponseEntity;

import com.service.auth.builder.request.MenuRoleRequest;

import jakarta.validation.Valid;

public interface MenuRoleService {

	ResponseEntity<?> rolemenulist(Locale locale, String role);

	ResponseEntity<?> save(Locale locale, String username, @Valid MenuRoleRequest req);

	ResponseEntity<?> menulist(Locale locale);

	ResponseEntity<?> remove(Locale locale, String username, String role);


}
