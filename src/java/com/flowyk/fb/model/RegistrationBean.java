/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.email.NoReplyEmailSession;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.entity.facade.RegistrationFacade;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

/**
 *
 * @author Lukas
 */
@Named(value = "registrationBean")
@RequestScoped
public class RegistrationBean implements Serializable {

    @EJB
    private RegisteredUserFacade registeredUserFacade;

    @EJB
    private RegistrationFacade registrationFacade;

    @Inject
    private ContestBean contestBean;

    @Inject
    private NoReplyEmailSession email;

    @Inject
    private Login login;

    @Inject
    private SignedRequest signedRequest;

    @AssertTrue
    private boolean acceptedRules = false;

    public boolean getAcceptedRules() {
        return acceptedRules;
    }

    public void setAcceptedRules(boolean acceptedRules) {
        this.acceptedRules = acceptedRules;
    }

    public String register() {
        RegisteredUser user = login.getUser();
        if (user != null) {

            RegisteredUser managedUser = registeredUserFacade.findByContestAndEmail(user.getContest(), user.getEmail());
            if (managedUser == null) {
                registeredUserFacade.create(user);

                RegisteredUser reference = null;
                Integer refId = signedRequest.getAppData().getReference();
                if (refId != null) {
                    reference = registeredUserFacade.find(refId);
                }
                Registration ticket = contestBean.createNewTicket(user, Constants.FIRST_REGISTRATION_TICKETS, reference);
                registrationFacade.create(ticket);

                email.sendRegistrationCompleteEmail(ticket);
                return "thanks";
            } else {
                login.setUser(managedUser);
                String msg = "Zadaný email už je zaregistrovaný";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                return "returning";
            }
        } else {
            throw new IllegalStateException("Trying to register with registered user set to null");
        }
    }

}
