package com.service.auth.builder.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.service.auth.config.SanitizedStringDeserializer;

public class SMSDetailsRq {

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String recipient;
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String msgBody;
	
	public SMSDetailsRq() {
		super();
	}
	
	public SMSDetailsRq(String recipient, String msgBody) {
		super();
		this.recipient = recipient;
		this.msgBody = msgBody;
	}

	public String getRecipient() {
		return recipient;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
}
