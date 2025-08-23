package com.service.auth.service;

import java.net.URL;
import java.time.Year;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.auth.builder.request.EmailDetailsRq;
import com.service.auth.config.EmailTemplate;
import com.service.auth.config.Utils;
import com.service.auth.model.Settings;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.URLDataSource;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SettingsService settingsService;
	
    public boolean sendSimpleMail(EmailDetailsRq details) {
        try {
            Settings settings = settingsService.returndefaultSettings();
     
            Properties props = new Properties();
            props.put("mail.smtp.auth", String.valueOf(settings.isMailauth()));
            props.put("mail.smtp.starttls.enable", String.valueOf(settings.isMailstarttlsenable()));
            props.put("mail.smtp.host", settings.getMailhost());
            props.put("mail.smtp.port", String.valueOf(settings.getMailport()));
            props.put("mail.smtp.connectiontimeout", "10000");
            props.put("mail.smtp.timeout", "10000");
            props.put("mail.smtp.writetimeout", "10000");
     
            // Optional: Add debug for authentication flow
            props.put("mail.debug.auth", "true");
     
            // Print debug info
            Utils.systemlog("SMTP Properties:", null);
            props.forEach((k, v) -> Utils.systemlog("  " + k + " = " + v, null));
            Utils.systemlog("SMTP Credentials:", null);
            Utils.systemlog("  Username = >" + settings.getMailusername() + "<", null);
            Utils.systemlog("  Password = >" + (settings.getMailpassword() != null ? "*****" : "null") + "<", null);
     
            Session session = Session.getInstance(props);
            session.setDebug(true);
     
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(settings.getMailfrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(details.getRecipient()));
     
            if (settings.getMailsupport() != null && !settings.getMailsupport().isEmpty()) {
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(settings.getMailsupport()));
            }
     
            try {
                String encodedSubject = MimeUtility.encodeText(details.getSubject(), "UTF-8", "B");
                message.setSubject(encodedSubject);
            } catch (Exception e) {
                message.setSubject(details.getSubject());
            }
            
            Multipart multipart = new MimeMultipart("related");
            
         // Create HTML part
            MimeBodyPart htmlPart = new MimeBodyPart();
            
            String emailTemplate = EmailTemplate.emailTemplate;
            emailTemplate = emailTemplate.replace("[Notification_Title_EN]", details.getSubject());
            emailTemplate = emailTemplate.replace("[Employee_Name_EN]", "");
            emailTemplate = emailTemplate.replace("[Notification_Message_EN]", details.getMsgBody());
            
//            String btndiv = EmailTemplate.btndiv;
//            btndiv = btndiv.replace("[BTN_URLLINK]", "");
//            btndiv = btndiv.replace("[BTN_Name]", "");
//          emailTemplate = emailTemplate.replace("[BTN_DIV]", btndiv);
            emailTemplate = emailTemplate.replace("[BTN_DIV]", "");

            emailTemplate = emailTemplate.replace("[Employee_Name_AR]", "");
            emailTemplate = emailTemplate.replace("[Notification_Message_AR]", details.getMsgBodyAr());
            String currentYear = String.valueOf(Year.now().getValue());
            emailTemplate = emailTemplate.replace("[CURRENT_YEAR]", currentYear);

            htmlPart.setContent(emailTemplate, "text/html; charset=UTF-8");
            multipart.addBodyPart(htmlPart);
            
            // Add logo attachment
            String logo = "https://internationalmissiondevie.org/images/mission-de-vie-logo.jpg";
            try {
                MimeBodyPart imagePart = new MimeBodyPart();
                // Download the image from URL and attach it
                URL imageUrl = new URL(logo);
                DataSource imageSource = new URLDataSource(imageUrl);
                imagePart.setDataHandler(new DataHandler(imageSource));
                imagePart.setHeader("Content-ID", "<attached_image>");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                imagePart.setFileName("logo.png");
                multipart.addBodyPart(imagePart);
            } catch (Exception imgEx) {
                Utils.systemlog("Error attaching logo: " + imgEx.getMessage(), null);
            }
           
            // Set the multipart content
            message.setContent(multipart);
      
            // Manual SMTP connection using credentials
            Transport transport = session.getTransport("smtp");
            transport.connect(
                settings.getMailhost(),
                settings.getMailport(),
                settings.getMailusername(),
                settings.getMailpassword()
            );
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
     
            return true;
     
        } catch (Exception e) {
        	Utils.systemlog(">> Error while sending email: " + e.getMessage(), null);
            e.printStackTrace();
            return false;
        }
    }

}