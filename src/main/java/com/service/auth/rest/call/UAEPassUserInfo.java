package com.service.auth.rest.call;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.service.auth.config.Constants;

public class UAEPassUserInfo extends RestCallHandler{ 
	
	private UAEPassAccessToken uaePassAccessToken;
	
	public UAEPassUserInfo(String url, String uaepassaccessurl, String uaepassusername, String uaepasspassword) {
		super(url);
 
		uaePassAccessToken = new UAEPassAccessToken(uaepassaccessurl, uaepassusername, uaepasspassword);
	} 
	 
	@Override
	public void constructHeaders() {
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.APPLICATION_JSON);
		try { 
			String tokenResponse = uaePassAccessToken.callAsPost();
			JSONObject response = new JSONObject(tokenResponse);

			if(!response.has(Constants.ACCESS_TOKEN_KEY)) {
				return;
			}
			
			String token = response.getString(Constants.ACCESS_TOKEN_KEY);
 
			this.headers.set("Authorization", Constants.TOKEN_TYPE_BEARER + token);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void constructBody() {
		
	}

}
