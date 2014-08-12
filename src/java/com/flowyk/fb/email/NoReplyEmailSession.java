/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Lukas
 */
@Stateless
@LocalBean
public class NoReplyEmailSession {

    private static final Logger LOG = Logger.getLogger(NoReplyEmailSession.class.getName());

    @Resource(lookup = "noReplyCmcMail")
    private Session mailSession;
    
    private static final String[] languages = { "sk" };
    
    public void sendEmail(String sender, InternetAddress to, String subject, String body) {
        MimeMessage message = new MimeMessage(mailSession);
        try {
            InternetAddress from = new InternetAddress(mailSession.getProperty("mail.from"));
            from.setPersonal(sender, "UTF-8");
            message.setFrom(from);
            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setContentLanguage(languages);
            message.setText(body, "UTF-8");
            
            //Multipart - if html part needed
//            Multipart multipart = new MimeMultipart("alternative");
//            MimeBodyPart textPart = new MimeBodyPart();
//            String textContent = "Hi, Nice to meet you!";
//            textPart.setText(textContent);
//
//            MimeBodyPart htmlPart = new MimeBodyPart();
//            String htmlContent = "<html><h1>Hi</h1><p>Nice to meet you!</p></html>";
//            htmlPart.setContent(htmlContent, "text/html");
//            multipart.addBodyPart(textPart);
//            multipart.addBodyPart(htmlPart);
//            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NoReplyEmailSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
