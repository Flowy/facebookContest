/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.session;

import com.flowyk.fb.model.session.Login;
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
import com.flowyk.fb.model.session.Page;
import com.flowyk.fb.model.session.SignedRequest;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Lukas
 */
@Named
@RequestScoped
public class ContestBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(ContestBean.class.getName());

    private Contest activeContest = null;

    @EJB
    private CustomRegistrationFacade registrationFacade;

    @EJB
    private CustomContestFacade contestFacade;

    @Inject
    private Page pageBean;

    @Inject
    Login contestUser;

    private boolean returning = false;

    // Actions -----------------------------------------------------------------------------------
    /**
     * expects open transaction and persisted user
     */
    Registration createNewTicket(RegisteredUser forUser, int weight, RegisteredUser referal) {
        Registration ticket = new Registration();
        ticket.setRegisteredUser(forUser);
        ticket.setTimeRegistered(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
        ticket.setIpAddress(contestUser.getIpAddress());
        ticket.setUserAgent(contestUser.getUserAgent());
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

        if (pageBean.getPage() != null && pageBean.getPage().getActive()) {
            List<Contest> contestList = contestFacade.findByPage(pageBean.getPage());
//          TODO: why this doesnt work ... List<Contest> list = new ArrayList(page.getContestCollection());
            Contest active = selectActiveContest(contestList);
            return active;
        } else {
            throw new FBPageNotActiveException("Page: " + pageBean.getPage());
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

    public String getImageUrl(String image, Contest contest) {
        if (contest != null) {
            String pageId = contest.getRegisteredPage().getPageId();
            return "/images/" + pageId + "/" + image;
        } else {
            throw new InvalidParameterException("Contest is null");
        }
    }

    /**
     *
     * @param image with starting slash
     * @return image url for actual contest
     */
    public String getImageUrl(String image) {
        return getImageUrl(image, getActiveContest());
    }

    public boolean getReturning() {
        return returning;
    }

    // Setters -----------------------------------------------------------------------------------
    public void setReturning(boolean value) {
        this.returning = value;
    }
}
