package com.service.auth.service;

import java.util.Locale;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.auth.builder.request.SMSDetailsRq;
import com.service.auth.builder.response.MessageResponse;
import com.service.auth.config.Constants;
import com.service.auth.model.Settings;
import com.service.auth.rest.call.SendSMS;

@Service
public class SMSServiceImpl implements SMSService {

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private MessageService messageService;
	
	public ResponseEntity<?> sendSMS(Locale locale, SMSDetailsRq details){

		try {
	   	    Settings settings = settingsService.returndefaultSettings();
			SendSMS sendSMS = new SendSMS(settings.getSmsauthapi(), settings.getSmsauthusername(), settings.getSmsauthpassword(), settings.getSmsapi(), settings.getSmsapplicationid(), settings.getSmspassword(), details.getRecipient(), details.getMsgBody());
			String res = sendSMS.callAsPost();
			if (res == null) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("error_calling_send_sms", locale), 301);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}
	
			JSONObject resjson = new JSONObject(res);

			if (resjson == null || !resjson.has("response")) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("error_parsing_send_sms_resp", locale), 302);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}
			
			JSONObject resdata = resjson.getJSONObject("response");
			
			if (resdata == null || !resdata.has("header")) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("error_parsing_send_sms_resp_header", locale), 303);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}
		
			JSONObject resheader = resdata.getJSONObject("header");
			
			if (resheader == null || !resheader.has("status")) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("error_parsing_send_sms_resp_header_status", locale), 304);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}

			String resstatus = resheader.getString("status");
			
			if (!resstatus.equals(Constants.SUCCESS_KEY)) {

				MessageResponse messageResponse = new MessageResponse(messageService.getMessage("send_sms_failed", locale), 305);
				return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();

			MessageResponse messageResponse = new MessageResponse(messageService.getMessage("error_sending_sms", locale), 306);
			return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
		}
		
		return null;
	}

}
