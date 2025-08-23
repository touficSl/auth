package com.service.auth.config;

public class EmailTemplate {

    // Perfect centered button with enhanced Outlook compatibility and perfect text centering
    public static String btndiv =
            """
<!-- Perfect centered button container -->
<table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%" style="margin: 30px 0;">
    <tr>
        <td align="center">
            <!-- Button table -->
            <table role="presentation" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto;">
                <tr>
                    <!--[if mso]>
                    <td>
                        <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word" href="[BTN_URLLINK]" style="height:52px; v-text-anchor:middle; width:240px;" arcsize="15%" stroke="f" fillcolor="#0a8043">
                            <w:anchorlock/>
                            <center style="color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold; text-decoration:none;">
                                [BTN_Name]
                            </center>
                        </v:roundrect>
                    </td>
                    <![endif]-->
                    
                    <!--[if !mso]><!-->
                    <td align="center" valign="middle" bgcolor="#0a8043" style="background-color: #0a8043; border-radius: 8px; height: 52px; width: 240px;">
                        <a href="[BTN_URLLINK]" 
                           style="background-color: #0a8043; 
                                  border: 0; 
                                  color: #ffffff; 
                                  font-family: Arial, Helvetica, sans-serif; 
                                  font-size: 16px; 
                                  font-weight: bold; 
                                  text-align: center; 
                                  text-decoration: none; 
                                  width: 240px; 
                                  height: 52px;
                                  display: table-cell;
                                  vertical-align: middle;
                                  border-radius: 8px;
                                  padding: 0;
                                  box-sizing: border-box;"
                           target="_blank">
                            [BTN_Name]
                        </a>
                    </td>
                    <!--<![endif]-->
                </tr>
            </table>
        </td>
    </tr>
</table>
            """;


    // Optimized email template for perfect Outlook rendering
    public static String emailTemplate =
            """
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="x-apple-disable-message-reformatting">
        <meta name="format-detection" content="telephone=no,address=no,email=no,date=no,url=no">
        
        <!--[if mso]>
        <noscript>
            <xml>
                <o:OfficeDocumentSettings>
                    <o:AllowPNG/>
                    <o:PixelsPerInch>96</o:PixelsPerInch>
                </o:OfficeDocumentSettings>
            </xml>
        </noscript>
        <![endif]-->
        
        <title>Mission De vie</title>
        
        <style type="text/css">
            /* Font definition */
            @font-face {
                font-family: 'Arial';
                font-weight: normal;
                font-style: normal;
                font-display: swap;
            }
            
            /* Reset styles */
            * {
                box-sizing: border-box;
            }
            
            body, html {
                font-family: Arial, Helvetica, sans-serif; /* Default to Arial for English */
                margin: 0 !important;
                padding: 0 !important;
                color: #333333;
                background-color: #f2f2f2;
                width: 100% !important;
                height: 100% !important;
                -webkit-text-size-adjust: 100%;
                -ms-text-size-adjust: 100%;
            }
            
            /* Table reset */
            table {
                border-collapse: collapse !important;
                mso-table-lspace: 0pt !important;
                mso-table-rspace: 0pt !important;
            }
            
            table, td {
                border-collapse: collapse;
                mso-line-height-rule: exactly;
            }
            
            /* Image styles */
            img {
                border: 0;
                height: auto;
                line-height: 100%;
                outline: none;
                text-decoration: none;
                -ms-interpolation-mode: bicubic;
                max-width: 100%;
            }
            
            /* Link styles */
            a {
                text-decoration: none;
                color: #0a8043;
            }
            
            /* Container styles */
            .email-container {
                max-width: 600px;
                margin: 0 auto;
                background-color: #ffffff;
            }
            
            .header-section {
                background-color: #f9f9f9;
                border-bottom: 3px solid #0a8043;
            }
            
            .content-section {
                background-color: #ffffff;
                padding: 30px 25px;
            }
            
            .footer-section {
                background-color: #333333;
                color: #ffffff;
                padding: 25px;
            }
            
            /* Typography - English content uses Arial */
            .title {
                font-family: Arial, Helvetica, sans-serif;
                font-size: 22px;
                font-weight: bold;
                color: #0a8043;
                margin: 0 0 20px 0;
                line-height: 1.3;
            }
            
            .message {
                font-family: Arial, Helvetica, sans-serif;
                font-size: 16px;
                line-height: 1.6;
                color: #333333;
                margin: 0 0 20px 0;
            }
            
            .message p {
                margin: 0 0 16px 0;
                padding: 0;
            }
            
            /* Arabic specific - Use AvenirArabic only for Arabic text */
            .arabic-content {
                direction: rtl !important;
                text-align: right !important;
                font-family: 'AvenirArabic', Arial, Helvetica, sans-serif !important;
            }
            
            .arabic-content p {
                font-family: 'AvenirArabic', Arial, Helvetica, sans-serif !important;
            }
            
            /* Footer styles */
            .footer-text {
                font-size: 14px;
                line-height: 1.4;
                color: #ffffff;
                margin: 0 0 15px 0;
            }
            
            .contact-info {
                font-size: 12px;
                line-height: 1.5;
                color: #cccccc;
                margin: 15px 0 0 0;
            }
            
            .social-links {
                margin: 15px 0;
            }
            
            .social-link {
                display: inline-block;
                margin: 0 8px;
                color: #ffffff;
                text-decoration: none;
                font-size: 18px;
            }
            
            /* Outlook specific styles */
            <!--[if mso]>
            .email-container {
                width: 600px !important;
            }
            
            .content-section {
                padding: 30px 25px !important;
            }
            
            .arabic-content {
                direction: rtl !important;
                text-align: right !important;
                font-family: 'AvenirArabic', Arial, Helvetica, sans-serif !important;
            }
            
            .arabic-content p {
                font-family: 'AvenirArabic', Arial, Helvetica, sans-serif !important;
            }
            
            .message {
                font-family: Arial, Helvetica, sans-serif !important;
            }
            
            .title {
                font-family: Arial, Helvetica, sans-serif !important;
            }
            
            table, td {
                border-collapse: collapse !important;
                mso-line-height-rule: exactly !important;
            }
            <![endif]-->
            
            /* Mobile styles */
            @media only screen and (max-width: 600px) {
                .email-container {
                    width: 100% !important;
                    max-width: 100% !important;
                }
                
                .content-section {
                    padding: 20px 15px !important;
                }
                
                .title {
                    font-size: 20px !important;
                }
                
                .message {
                    font-size: 15px !important;
                }
                
                .header-logo {
                    max-width: 280px !important;
                }
            }
        </style>
    </head>
    
    <body style="margin: 0; padding: 0; background-color: #f2f2f2;">
        <!--[if mso | IE]>
        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="background-color: #f2f2f2;">
            <tr>
                <td>
        <![endif]-->
        
        <!-- Main container -->
        <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%" style="background-color: #f2f2f2;">
            <tr>
                <td align="center" style="padding: 20px 0;">
                    
                    <!--[if mso | IE]>
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="600" class="email-container">
                        <tr>
                            <td style="line-height:0px;font-size:0px;mso-line-height-rule:exactly;">
                    <![endif]-->
                    
                    <!-- Email container -->
                    <table class="email-container" role="presentation" cellspacing="0" cellpadding="0" border="0" width="600" style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 2px solid #0a8043; border-radius: 8px;">
                        
                        <!-- Header with Logo -->
                        <tr>
                            <td class="header-section" align="center" style="background-color: #f9f9f9; padding: 25px; border-bottom: 3px solid #0a8043;">
                                <img src="cid:attached_image" alt="Mission De Vie" class="header-logo" width="250" style="max-width: 250px; height: auto; display: block; margin: 0 auto;">
                            </td>
                        </tr>
                        
                        <!-- Main Content -->
                        <tr>
                            <td class="content-section" style="background-color: #ffffff; padding: 30px 25px;">
                                
                                <!-- Title -->
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                    <tr>
                                        <td>
                                            <h1 class="title" style="font-family: Arial, Helvetica, sans-serif; font-size: 22px; font-weight: bold; color: #0a8043; margin: 0 0 20px 0; line-height: 1.3;">
                                                NOTIFICATION: [Notification_Title_EN]
                                            </h1>
                                        </td>
                                    </tr>
                                </table>
                                
                                <!-- English Content -->
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                    <tr>
                                        <td>
                                            <div class="message" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; line-height: 1.6; color: #333333; margin: 0 0 20px 0;">
                                                <p style="margin: 0 0 16px 0; font-family: Arial, Helvetica, sans-serif;">Dear [Employee_Name_EN],</p>
                                                <p style="margin: 0 0 16px 0; font-family: Arial, Helvetica, sans-serif;">This is a notification from Mission De Vie system.</p>
                                                <div style="margin: 16px 0; font-family: Arial, Helvetica, sans-serif;">[Notification_Message_EN]</div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                
                                <!-- Button Section - Perfectly Centered -->
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                    <tr>
                                        <td align="center" style="padding: 10px 0;">
                                            [BTN_DIV]
                                        </td>
                                    </tr>
                                </table>
                                
                                <!-- Thank You -->
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                    <tr>
                                        <td>
                                            <div class="message" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; line-height: 1.6; color: #333333; margin: 20px 0;">
                                                <p style="margin: 0 0 16px 0; font-family: Arial, Helvetica, sans-serif;">Thank you,</p>
                                                <p style="margin: 0; font-family: Arial, Helvetica, sans-serif;">Mission De Vie</p>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                
                                <!-- Arabic Content - AvenirArabic Font
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%" style="margin-top: 30px; border-top: 1px solid #eeeeee; padding-top: 25px;">
                                    <tr>
                                        <td>
                                            <div class="message arabic-content" dir="rtl" style="font-family: 'AvenirArabic', Arial, Helvetica, sans-serif; font-size: 16px; line-height: 1.6; color: #333333; direction: rtl; text-align: right;">
                                                <p style="margin: 0 0 16px 0; font-family: 'AvenirArabic', Arial, Helvetica, sans-serif;">عزيزي [Employee_Name_AR]،</p>
                                                <p style="margin: 0 0 16px 0; font-family: 'AvenirArabic', Arial, Helvetica, sans-serif;">هذا إشعار من.</p>
                                                <div style="margin: 16px 0; font-family: 'AvenirArabic', Arial, Helvetica, sans-serif;">[Notification_Message_AR]</div>
                                                <p style="margin: 16px 0 0 0; font-family: 'AvenirArabic', Arial, Helvetica, sans-serif;">شكرا لك،</p>
                                                <p style="margin: 0; font-family: 'AvenirArabic', Arial, Helvetica, sans-serif;"></p>
                                            </div>
                                        </td>
                                    </tr>
                                </table> -->
                                
                            </td>
                        </tr>
                        
                        <!-- Footer -->
                        <tr>
                            <td class="footer-section" style="background-color: #333333; color: #ffffff; padding: 25px; text-align: center;">
                                
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                    <tr>
                                        <td align="center">
                                            <div class="footer-text" style="font-size: 14px; line-height: 1.4; color: #ffffff; margin: 0 0 15px 0;">
                                                © [CURRENT_YEAR] Mission De Vie
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                
                                <!-- Social Links -->
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                    <tr>
                                        <td align="center" class="social-links" style="margin: 15px 0;">
                                            <a href="https://www.instagram.com/international_mission_de_vie/" class="social-link" style="display: inline-block; margin: 0 8px; color: #ffffff; text-decoration: none; font-size: 18px;">📷</a>
                                            <a href="https://www.youtube.com/@internationalmissiondevie" style="display: inline-block; margin: 0 8px; color: #ffffff; text-decoration: none; font-size: 18px;">📺</a>
                                            <a href="https://www.facebook.com/InternationalMissionDeVie" class="social-link" style="display: inline-block; margin: 0 8px; color: #ffffff; text-decoration: none; font-size: 18px;">📘</a>
                                        </td>
                                    </tr>
                                </table>
                                
                                <!-- Contact Info -->
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                    <tr>
                                        <td align="center">
                                            <div class="contact-info" style="font-size: 12px; line-height: 1.5; color: #cccccc; margin: 15px 0 0 0;">
                                                Headquarters: Switzerland, Lens<br>
                                                Phone: +33 6 70 55 20 09 | +961 4 40 50 67<br>
                                                <a href="https://internationalmissiondevie.org/privacypolicy" style="color: #ffffff; text-decoration: underline;">Privacy Policy</a>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                
                            </td>
                        </tr>
                        
                    </table>
                    
                    <!--[if mso | IE]>
                            </td>
                        </tr>
                    </table>
                    <![endif]-->
                    
                </td>
            </tr>
        </table>
        
        <!--[if mso | IE]>
                </td>
            </tr>
        </table>
        <![endif]-->
    </body>
</html>
            """;

}