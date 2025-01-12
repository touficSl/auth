package com.service.auth.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.auth.builder.response.AuthTypeResponse;
import com.service.auth.enumeration.AuthTypeEnum;

public class Utils {

	public static String generate6Digits() {
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}

	public static String convertDateToString(Date date, String dateformat) {
		if (date == null)
			return null;
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
        return formatter.format(date);
	}

	public static boolean isapiauthorized(String url, List<String> authorizedapis) {
		if (url == null) 
			return true;
		for (String authapi : authorizedapis)
			if (url.contains(authapi))
				return true;
		return false;
	}

	public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        return mapper.writeValueAsString(object);
    }

	public static Date convertStringToDate(String dateString, String dateformat) {
        if (dateString == null)
        	return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
        return null;
	}
	
	public static boolean isPasswordComplex(String password) {
	    String regex = "^.{6,}$"; // Matches any string with at least 6 characters
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }
	
	public static List<String> getRequiredPassAuthType() {

        List<String> requiredPassAuthType = new ArrayList<>();

        for (AuthTypeEnum authType : AuthTypeEnum.values()) {
            if (authType.isRequirepassword()) {
            	requiredPassAuthType.add(authType.name());
            }
        }
        
        return requiredPassAuthType;
	}
	
	public static List<AuthTypeResponse> getAuthTypeResponse() {

        List<AuthTypeResponse> authTypeResponse = new ArrayList<>();

        for (AuthTypeEnum authType : AuthTypeEnum.values()) {
        	authTypeResponse.add(new AuthTypeResponse(authType.name(), authType.isRequirepassword()));            
        }
        
        return authTypeResponse;
	}

	public static boolean isrequiredpass(String auth_type) {

        for (AuthTypeEnum authType : AuthTypeEnum.values()) {
            if (authType.name().equals(auth_type))
        		return authType.isRequirepassword() ? true : false;
        }
		return false;
	}
}
