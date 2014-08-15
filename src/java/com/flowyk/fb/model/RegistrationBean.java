/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.model.session.ContestBean;
import com.flowyk.fb.model.session.Login;
import com.flowyk.fb.base.Constants;
import com.flowyk.fb.email.NoReplyEmailSession;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.entity.facade.custom.CustomRegisteredUserFacade;
import com.flowyk.fb.model.session.AppData;
import com.flowyk.fb.model.session.SignedRequest;
import com.flowyk.fb.model.session.User;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@Named(value = "registrationBean")
@ViewScoped
public class RegistrationBean implements Serializable {

    @EJB
    private CustomRegisteredUserFacade registeredUserFacade;

    @Inject
    private ContestBean contestBean;
    
    @Inject
    private User userBean;

    @Inject
    private SignedRequest signedRequest;

    @Inject
    private NoReplyEmailSession email;

    @Inject
    private Login login;
    
    @Inject
    private AppData appDataBean;
    
    @AssertTrue
    private boolean acceptedRules = false;
    
//    TODO: register
//    public String register() {
//        activeUser.setContest(contestBean.getActiveContest());
//        userBean.setRegisteredUser(activeUser);
//
//        List<RegisteredUser> userList = registeredUserFacade.findByContestAndEmail(activeUser.getContest(), activeUser.getEmail());
//
//        if (userList.isEmpty()) {
//            registeredUserFacade.create(activeUser);
//            
//            
//            Registration ticket = contestBean.createNewTicket(activeUser, Constants.FIRST_REGISTRATION_TICKETS, 
//                    appDataBean.getReference());
//            email.sendRegistrationCompleteEmail(ticket);
//
//            contestBean.setReturning(Boolean.TRUE);
//            return "thanks";
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email už je zaregistrovaný", "Zadaný email už je zaregistrovaný"));
//            contestBean.setReturning(Boolean.TRUE);
//            return "returning";
//        }
//    }

    public boolean getAcceptedRules() {
        return acceptedRules;
    }

    public void setAcceptedRules(boolean acceptedRules) {
        this.acceptedRules = acceptedRules;
    }

}
