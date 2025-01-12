package com.service.auth.rest.call;

import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.service.auth.config.Constants;

public class UAEPassAccessToken extends RestCallHandler{

	private String uaepassusername;
	private String uaepasspassword;
	
	public UAEPassAccessToken(String url, String uaepassusername, String uaepasspassword) {
		super(url);
		this.uaepassusername = uaepassusername;
		this.uaepasspassword = uaepasspassword;
	}

	@Override
	public void constructHeaders() {
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		try { 
 
			String encodedAuthorizationField = getEncodedAuthorizationField(getUaepassusername(), getUaepasspassword());

			this.headers.set("Authorization", Constants.TOKEN_TYPE_BASIC + encodedAuthorizationField); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	String getEncodedAuthorizationField(String username, String password) {
		final String authorizationField = username + ":" + password;

		/* Get the Encoded authorization Field */
		byte[] authorizationFieldByteArray = authorizationField.getBytes();
		String bytesEncoded = Base64.getEncoder().encodeToString(authorizationFieldByteArray);
		String authorizationFieldEncoded = new String(bytesEncoded);

		return authorizationFieldEncoded;
	}

	@Override
	public void constructBody() {
		
	}

	public String getUaepassusername() {
		return uaepassusername;
	}

	public void setUaepassusername(String uaepassusername) {
		this.uaepassusername = uaepassusername;
	}

	public String getUaepasspassword() {
		return uaepasspassword;
	}

	public void setUaepasspassword(String uaepasspassword) {
		this.uaepasspassword = uaepasspassword;
	}
	
	
	
}
