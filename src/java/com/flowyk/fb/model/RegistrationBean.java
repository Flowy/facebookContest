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
import com.flowyk.fb.entity.facade.custom.CustomRegisteredUserFacade;
import com.flowyk.fb.model.signedrequest.SignedRequest;
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

    @NotNull
    private final RegisteredUser activeUser;

    @AssertTrue
    private boolean acceptedRules = false;

    @Inject
    private ContestBean contestBean;

    @Inject
    private SignedRequest signedRequest;

    @Inject
    NoReplyEmailSession email;

    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
        activeUser = new RegisteredUser();
    }

    public String register() {
        activeUser.setContest(contestBean.getActiveContest());
        activeUser.setLocale(signedRequest.getUser().getLocale());
        activeUser.setCountry(signedRequest.getUser().getCountry());
        activeUser.setAgeMax(signedRequest.getUser().getAgeMax());
        activeUser.setAgeMin(signedRequest.getUser().getAgeMin());

        List<RegisteredUser> userList = registeredUserFacade.findByContestAndEmail(activeUser.getContest(), activeUser.getEmail());

        if (userList.isEmpty()) {
            registeredUserFacade.create(activeUser);
            signedRequest.getUser().setId(activeUser.getId());
            int refId = signedRequest.getAppData().getReference();
            RegisteredUser referal = null;
            if (refId != 0) {
                referal = registeredUserFacade.find(refId);
            }
            Registration ticket = contestBean.createNewTicket(activeUser, Constants.FIRST_REGISTRATION_TICKETS, referal);
            email.sendRegistrationCompleteEmail(ticket);

            contestBean.setReturning(Boolean.TRUE);
            return "thanks";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email už je zaregistrovaný", "Zadaný email už je zaregistrovaný"));
            contestBean.setReturning(Boolean.TRUE);
            return "returning";
        }
    }

    public RegisteredUser getActiveUser() {
        return activeUser;
    }

    public boolean getAcceptedRules() {
        return acceptedRules;
    }

    public void setAcceptedRules(boolean acceptedRules) {
        this.acceptedRules = acceptedRules;
    }

}
