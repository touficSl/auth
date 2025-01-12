package com.service.auth.config;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.auth.model.Settings;
import com.service.auth.service.SettingsService;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Autowired
    private SettingsService settingsService;

	public String generateJwtToken(String username, Boolean shortexp) {

		Date expriydate = returnJwtExpriyDate(shortexp);
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(expriydate)
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	private Key key() {
   	    Settings settings = settingsService.returndefaultSettings();
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(settings.getJwtsecret()));
	}

	public String getUserNameFromJwtToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean validateJwtToken(String token, String username) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
			
			if (!getUserNameFromJwtToken(token).equals(username))
				return false;
			
			return true;
			
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		} catch (Exception e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public Date returnJwtExpriyDate(Boolean shortexp) {
   	    Settings settings = settingsService.returndefaultSettings();
		if (shortexp == null)
			return new Date((new Date()).getTime() + settings.getJwtexpirationmscode());
		return shortexp ? new Date((new Date()).getTime() + settings.getJwtexpirationmsshort()) : new Date((new Date()).getTime() + settings.getJwtexpirationms());
	}
}
