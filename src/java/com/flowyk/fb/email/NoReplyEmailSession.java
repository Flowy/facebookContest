/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.email;

import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.model.Login;
import com.flowyk.fb.model.ShareUrlBean;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
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
    
    @Inject
    Login login;
    
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
            
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("com.flowyk.fb.i18n.Bundle", locale);
            
            String title = bundle.getString("email.title");
            String regComplete = bundle.getString("email.regComplete");
            String link = shareUrl.getFBShareUrl();
            String pageLinkText = bundle.getString("email.facebookPageLink");
            String comeAgainString = bundle.getString("email.comeAgain");
            String shareTheLink = bundle.getString("email.shareTheLink");
            String endsComeString = bundle.getString("email.endsCome");
            String endsCome = MessageFormat.format(endsComeString, login.getContest().getContestEnd());
            String holdsYourThumbs = bundle.getString("email.holdsYourThumbs");
            String dontReply = bundle.getString("email.dontReply");
            
            int delay = (int) login.getContest().getTimeBetweenTickets().getTime();
            Calendar calendar = Calendar.getInstance();
            int hoursDelay = (int) delay / (1000*60*60);
            delay -= hoursDelay;
            int minuteDelay = (int) delay / (1000*60);
            System.out.println("hours delay: " + hoursDelay + " minuteDelay: " + minuteDelay);
            
            calendar.add(Calendar.HOUR_OF_DAY, hoursDelay);
            calendar.add(Calendar.MINUTE, minuteDelay);
            Date comeAgainTime = calendar.getTime();
            
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
                    .append(MessageFormat.format(comeAgainString, pageLinkText, comeAgainTime)).append("\r\n\r\n")
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
                    .append(MessageFormat.format(comeAgainString, "<a href=\"" + link + "\" target=\"_blank\">" + pageLinkText + "</a>", comeAgainTime)).append("</p>")
                    .append("<p>").append(shareTheLink).append("<br />")
                    .append(link).append("</p>")
                    .append("<p>").append(endsCome).append("</p>")
                    .append("<p>").append(holdsYourThumbs).append("</p>")
                    .append("<p>").append(dontReply).append("</p>")
                    .append("</body></html>");
            
//            System.out.println(htmlSb.toString());
            
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
