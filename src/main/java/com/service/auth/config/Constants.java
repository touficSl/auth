package com.service.auth.config;

public class Constants {


	public static final String[] EXCLUDED_PATHS = {"/api/token/"};
	public static final String ADMIN_PATH = "/api/admin/";

	public static final String SUBJECT = "Auth - ";
	
	public static final String ACCESS_TOKEN_KEY = "access_token";

	public static final String TOKEN_TYPE_BEARER = "Bearer ";
	public static final String TOKEN_TYPE_BASIC = "Basic ";

	public static final String SUCCESS_KEY = "Success";

	public static final String VERIFY_OTP_TOKEN = "VERIFY_OTP_TOKEN_";
	public static final String REFRESH_TOKEN = "REFRESH_TOKEN_";
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN_";
	public static final String CHANGEPASS_TOKEN = "CHANGEPASS_TOKEN_";

	public static final String LOGOUT = "LOGOUT";
	public static final String FORCE_REVOKE = "FORCE_REVOKE";

	public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_LANG = "en";
	public static final String SEPARATOR = "_";
	
	public static final String GET = "get";
	public static final String POST = "post";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String CONFIGURATION = "configuration";
	public static final String GLOBAL = "global";
	public static final String NO_TEAM = "No Team";
}
