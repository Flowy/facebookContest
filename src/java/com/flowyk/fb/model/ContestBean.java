/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.entity.facade.custom.CustomContestFacade;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.entity.facade.custom.CustomRegistrationFacade;
import com.flowyk.fb.exceptions.FBPageNotActiveException;
import com.flowyk.fb.exceptions.NoActiveContestException;
import com.flowyk.fb.exceptions.PageIdNotFoundException;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Lukas
 */
@Named
@SessionScoped
public class ContestBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(ContestBean.class.getName());

    @Inject
    private SignedRequest signedRequest;

    @EJB
    private CustomRegistrationFacade registrationFacade;
    
    @EJB
    private CustomContestFacade contestFacade;

    @EJB
    private RegisteredPageFacade registeredPage;

    private boolean returning = false;


    // Actions -----------------------------------------------------------------------------------
    
    /**
     * expects open transaction and persisted user
     */
    Registration createNewTicket(RegisteredUser forUser, int weight, RegisteredUser referal) {
        Registration ticket = new Registration();
        ticket.setRegisteredUser(forUser);
        ticket.setTimeRegistered(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
        ticket.setIpAddress(signedRequest.getIpAddress());
        ticket.setUserAgent(signedRequest.getUserAgent());
        ticket.setReferal(referal);
        ticket.setWeight(weight);
        registrationFacade.create(ticket);
        return ticket;
    }

    // Getters -----------------------------------------------------------------------------------
    private Contest selectActiveContest(List<Contest> list) {
        Collections.sort(list, Collections.reverseOrder());
        Contest selected = null;
        Date now = new Date();
        for (Contest x : list) {
            if (!x.getDisabled() && x.getContestEnd().after(now)) {
                selected = x;
            }
        }
        if (selected != null) {
            return selected;
        } else {
            throw new NoActiveContestException();
        }
    }

    /**
     *
     * @return active contest for actual page from signed request
     * @throws PageIdNotFoundException if page not found in signed request
     */
    public Contest getActiveContest() {
        
        if (signedRequest.getPage().getId() != null) {
            RegisteredPage page = registeredPage.find(signedRequest.getPage().getId());
            if (page != null) {
//                System.out.println("Found page for: " + signedRequest.getPage().getId() + ", contests count: " + page.getContestCollection().size());
                List<Contest> contestList = contestFacade.findByPage(page);
//                List<Contest> list = new ArrayList(page.getContestCollection());
                Contest active = selectActiveContest(contestList);
                return active;
            } else {
                throw new FBPageNotActiveException("Page id: " + signedRequest.getPage().getId());
            }
        } else {
            throw new PageIdNotFoundException();
        }
    }

    /**
     *
     * @param page
     * @return
     */
    public String getPageUrl(String page) {
        Contest contest = getActiveContest();
        return "/WEB-INF/contest/layouts/" + contest.getContestLayout().getName() + "/" + page;
    }

    /**
     *
     * @param resource
     * @return
     */
    public String getResourceUrl(String resource) {
        Contest contest = getActiveContest();
        return "./contest/layouts/" + contest.getContestLayout().getName() + "/" + resource;
    }

    /**
     *
     * @param image with starting slash
     * @return
     */
    public String getImageUrl(String image) {
        if (signedRequest.getPage().getId() != null) {
            return "/images/" + signedRequest.getPage().getId() + "/" + image;
        } else {
            throw new PageIdNotFoundException();
        }
    }

    public boolean isPageActive() {
        String pageId = signedRequest.getPage().getId();
        if (pageId != null) {
            RegisteredPage page = registeredPage.find(pageId);
            if (page != null && page.getActive()) {
                return true;
            }
        }
        return false;
    }

    public boolean getReturning() {
        return returning;
    }

    // Setters -----------------------------------------------------------------------------------
    
    public void setReturning(boolean value) {
        this.returning = value;
    }
}
