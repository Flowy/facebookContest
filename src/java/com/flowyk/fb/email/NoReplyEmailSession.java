/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.email;

import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.model.ShareUrlBean;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Lukas
 */
@Stateless
@LocalBean
public class NoReplyEmailSession {

    private static final Logger LOG = Logger.getLogger(NoReplyEmailSession.class.getName());

    @Inject
    ShareUrlBean shareUrl;
    
    @Resource(lookup = "noReplyCmcMail")
    private Session mailSession;

    private static final String[] languages = {"sk"};

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

    public void sendRegistrationCompleteEmail(Registration reg) {
        MimeMessage message = new MimeMessage(mailSession);
        try {
            String title = "Zapojil si sa do súťaže";
            String link = shareUrl.getFBShareUrl(reg.getRegisteredUser());
            String regComplete = "Tvoja registrácia prebehla úspešne.";
            String comeAgain = String.format("Aby toho nebolo málo, máme pre teba prichystaný ďalší lístok. Stačí ak navštíviš našu facebookovú súťaž opäť %1$s a zadáš svoju emailovú adresu.", "date");
            String shareTheLink = "Svoju šancu na výhru môžes zvýšiť ak sa zaregistruje niekto ďalší cez tento link:";
            String endsCome = String.format("Losovanie výhercu prebehne %1$s  a meno výhercu bude uvdené na Facebook stránke. O prípadnej výhre ťa budeme informovať na tejto emailovej adrese.", "date");
            String holdsYourThumbs = "Držíme Ti palce!";
            String dontReply = "Táto správa bola vygenerovaná automaticky. Na túto správu prosím neodpovedajte.";
            
            InternetAddress from = new InternetAddress(mailSession.getProperty("mail.from"));
            from.setPersonal(reg.getRegisteredUser().getContest().getName(), "UTF-8");
            message.setFrom(from);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reg.getRegisteredUser().getEmail()));
            message.setSubject(title);
            message.setSentDate(new Date());
            message.setContentLanguage(languages);

            //Multipart - if html part needed
            Multipart multipart = new MimeMultipart("alternative");
            
            
            MimeBodyPart textPart = new MimeBodyPart();
            StringBuilder textSb = new StringBuilder();
            textSb
                    .append(regComplete).append("\r\n")
                    .append(comeAgain).append("\r\n\r\n")
                    .append(shareTheLink).append("\r\n")
                    .append(link).append("\r\n\r\n")
                    .append(endsCome).append("\r\n\r\n")
                    .append(holdsYourThumbs).append("\r\n\r\n")
                    .append(dontReply).append("\r\n");
            textPart.setText(textSb.toString(), "UTF-8");

            MimeBodyPart htmlPart = new MimeBodyPart();

            
            StringBuilder htmlSb = new StringBuilder();
            htmlSb
                    .append("<!DOCTYPE html>")
                    .append("<html><head><style>")
                    .append("body { font-family: Arial, Helvetica, sans-serif; font-size: 12px; }")
                    .append("p { line-height: 150%; margin-bottom: 17px; }")
                    .append("</style><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
                    .append("<title>").append(title).append("</title>")
                    .append("<head><body>")
                    .append("<p>").append(regComplete).append("<br />")
                    .append(comeAgain).append("</p>")
                    .append("<p>").append(shareTheLink).append("<br />")
                    .append(link).append("</p>")
                    .append("<p>").append(endsCome).append("</p>")
                    .append("<p>").append(holdsYourThumbs).append("</p>")
                    .append("<p>").append(dontReply).append("</p>")
                    .append("</body></html>");
            htmlPart.setContent(htmlSb.toString(), "text/html; charset=UTF-8");
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NoReplyEmailSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
