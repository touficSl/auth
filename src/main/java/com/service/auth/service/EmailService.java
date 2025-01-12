package com.service.auth.service;

import com.service.auth.builder.request.EmailDetailsRq;

public interface EmailService {

	boolean sendSimpleMail(EmailDetailsRq details);

}
