package com.service.auth.service;

import java.util.Locale;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.auth.builder.response.MessageResponse;
import com.service.auth.model.Settings;
import com.service.auth.rest.call.GoogleRecaptcha;


@Service
public class GoogleRecaptchaServiceImpl implements GoogleRecaptchaService {

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private MessageService messageService;
	
	@Override
	public ResponseEntity<?> verifyRecaptcha(Locale locale, String token) {

		try {

			if (token == null || token.trim().equals("")) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("recaptcha_required", locale), 410);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}

	   	    Settings settings = settingsService.returndefaultSettings();
			GoogleRecaptcha googleRecaptcha = new GoogleRecaptcha(settings.getRecaptchaapi(), token, settings.getRecaptchasitekey());
			String recaptchaRes = googleRecaptcha.callAsPost();
			if (recaptchaRes == null) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("recaptcha_request_error", locale), 411);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}
	
			JSONObject recaptcharesponse = new JSONObject(recaptchaRes);

			if (recaptcharesponse == null || !recaptcharesponse.has("tokenProperties")) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("recaptcha_parsing_response_error", locale), 412);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}
			
			JSONObject tokenProperties = recaptcharesponse.getJSONObject("tokenProperties");
			
			if (tokenProperties == null || !tokenProperties.has("valid") || !tokenProperties.getBoolean("valid")) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("recaptcha_verifying_validity_error", locale), 413);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}
				
		} catch (Exception e) {
			e.printStackTrace();

			MessageResponse messageResponse = new MessageResponse(messageService.getMessage("recaptcha_verifying_error", locale), 414);
			return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			
		}
		return null;
	}

}
