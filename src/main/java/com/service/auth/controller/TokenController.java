package com.service.auth.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.auth.config.Constants;
import com.service.auth.service.TokensService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/token")
public class TokenController {

	@Autowired
	private TokensService tokenService;
	
	@RequestMapping(value = "/list/count", method = RequestMethod.POST)
	public ResponseEntity<?> listcount(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

		return tokenService.listcount(locale);
	}
	

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
								  @RequestHeader(name = "all", required = false, defaultValue = "true") Boolean all,
								  @RequestHeader(name = "page", required = false, defaultValue = "0") Integer page,
								  @RequestHeader(name = "size", required = false, defaultValue = "0") Integer size,
								  @RequestHeader(name = "search", required = false) String search,
								  @RequestHeader(name = "sortcolumn", required = false) String sortcolumn,
								  @RequestHeader(name = "descending", required = false, defaultValue = "false") Boolean descending,
						          @RequestHeader(name = "draw", required = false, defaultValue = "1") Integer draw) {
		
		return tokenService.list(locale, all, page, size, search, sortcolumn, descending, draw, null);
	}

	@RequestMapping(value = "/force/revoke", method = RequestMethod.POST)
	public ResponseEntity<?> forcerevoke(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
										 @RequestHeader(name = "token", required = true) String token, 
										 @RequestHeader(name = "refreshtoken", required = true) String refreshtoken, 
										 @RequestHeader(name = "refreshkey", required = true) String refreshkey) {

		return tokenService.revoke(locale, refreshtoken, refreshkey, Constants.FORCE_REVOKE);
	}
}
