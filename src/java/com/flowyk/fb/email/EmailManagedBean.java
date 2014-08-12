/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.email;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Lukas
 */
@Named(value = "emailManagedBean")
@RequestScoped
public class EmailManagedBean {

    @EJB
    NoReplyEmailSession emailBean;
    
    private String sender;
    private String to;
    private String subject;
    private String body;

    // Actions -----------------------------------------------------------------------------------
    
    public void sendEmail() {

        try {
            InternetAddress ia = new InternetAddress(to);
            ia.validate();
            emailBean.sendEmail(sender, ia, subject, body);
        } catch (AddressException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "Recipient address not valid"));
        }
    }
    
    // Setters -----------------------------------------------------------------------------------
    
    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    // Getters -----------------------------------------------------------------------------------

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
