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
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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

    public String getTest() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(RegisteredUser.class, ContestUsers.class);
        
        ContestUsers contestUsers = new ContestUsers(users);
//        RegisteredUser user = users.get(0);
        
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        m.marshal(contestUsers, outputStream);
        String output = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        return output;
        
    }

    public StreamedContent getXMLExport() throws JAXBException {
        InputStream inputStream = null;
        String name = "name.xml";
        String mime = "application/xml";
        StreamedContent stream = new DefaultStreamedContent(inputStream, mime, name);
        return stream;
    }

    // Setters -----------------------------------------------------------------------------------
    public void setContestString(String contestString) {
        this.contestString = contestString;
    }

}
