package com.service.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.auth.builder.request.EmailDetailsRq;
import com.service.auth.model.Settings;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import java.util.Properties;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SettingsService settingsService;
	
	public boolean sendSimpleMail(EmailDetailsRq details) {
		  
		try {
	   	    Settings settings = settingsService.returndefaultSettings();
			
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", settings.isMailstarttlsenable()+"");
			props.put("mail.smtp.host", settings.getMailhost());
			props.put("mail.smtp.port", settings.getMailport());
		    props.put("mail.smtp.auth", settings.isMailauth()+"");
		    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(settings.getMailusername(), settings.getMailpassword());
				}
			});
			session.setDebug(true);
			
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(settings.getMailfrom()));

			try {
				InternetAddress[] toAddress = new InternetAddress[1];
				toAddress[0] = new InternetAddress(details.getRecipient());
				message.setRecipients(RecipientType.TO, toAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				InternetAddress[] bccAddress = new InternetAddress[1];
				bccAddress[0] = new InternetAddress(settings.getMailsupport());
				message.setRecipients(RecipientType.BCC, bccAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}

		    String encodedSubject = MimeUtility.encodeText(details.getSubject(), "UTF-8", "B");
		    message.setSubject(encodedSubject);

			message.setContent(details.getMsgBody(), "text/html; charset=UTF-8");

			Transport.send(message);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
