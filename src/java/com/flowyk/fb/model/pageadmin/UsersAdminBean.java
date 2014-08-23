/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.pageadmin;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.jaxb.ContestUsers;
import com.flowyk.fb.model.Login;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Lukas
 */
@Named
@ViewScoped
public class UsersAdminBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(UsersAdminBean.class.getName());

    @Inject
    SignedRequest signedRequest;

    @Inject
    Login login;
    
    @EJB
    ContestFacade contestService;

    @EJB
    RegisteredUserFacade registeredUserFacade;

    List<RegisteredUser> users;
    private String contestString = null;
    private Contest contest;

    // Actions -----------------------------------------------------------------------------------
    public void init() {
        if (contestString != null) {
            try {
                Integer contestId = Integer.valueOf(contestString);
                contest = contestService.find(contestId);
                boolean pageOk = contest.getRegisteredPage().getPageId().equals(signedRequest.getPage().getPageId());
                if (contest == null || !pageOk) {
                    redirectInvalidContest();
                } else {
                    users = registeredUserFacade.findByContest(contest);
                }
            } catch (NumberFormatException e) {
                LOG.log(Level.INFO, "ContestId not valid: " + contestString, e);
                redirectInvalidContest();
            }
        } else {
            System.out.println("Should redirect from users admin bean to error: contestString null");
            redirectInvalidContest();
        }
    }

    public void redirectInvalidContest() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Contest");
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

    public void addRemovedMessage(RegisteredUser user) {
        registeredUserFacade.edit(user);
        String summary = user.getRemoveFromContest() ? "Vyradený" : "Učasť povolená";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    // Getters -----------------------------------------------------------------------------------

    public String getContestString() {
        return contestString;
    }

    public List<RegisteredUser> getRegisteredUsers() {
        return users;
    }

    // Setters -----------------------------------------------------------------------------------
    public void setContestString(String contestString) {
        this.contestString = contestString;
    }

}
