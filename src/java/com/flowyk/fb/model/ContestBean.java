/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.entity.facade.RegistrationFacade;
import com.flowyk.fb.exceptions.NoActiveContestException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Calendar;
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

    @Inject
    private Login login;

    // Actions -----------------------------------------------------------------------------------
    /**
     *
     * @param forUser
     * @param weight
     * @return same as createNewTicket(forUser, weight, null);
     */
    public Registration createNewTicket(RegisteredUser forUser, int weight) {
        return createNewTicket(forUser, weight, null);
    }

    /**
     *
     * @param forUser
     * @param weight
     * @param referal
     * @return new registration
     */
    public Registration createNewTicket(RegisteredUser forUser, int weight, RegisteredUser referal) {
        Registration ticket = new Registration();
        ticket.setRegisteredUser(forUser);
        ticket.setTimeRegistered(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
        ticket.setIpAddress(login.getIpAddress());
        ticket.setUserAgent(login.getUserAgent());
        ticket.setReferal(referal);
        ticket.setWeight(weight);
        return ticket;
    }

    // Getters -----------------------------------------------------------------------------------

    /**
     *
     * @return active contest for actual page from signed request
     * @throws NoActiveContestException
     */
    public Contest getActiveContest() {
        if (login.getUser().getContest() != null) {
            return login.getUser().getContest();
        } else {
            throw new NoActiveContestException();
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

    public String getShareImgUrl(String image, Contest contest) {
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
    public String getShareImgUrl(String image) {
        return getShareImgUrl(image, getActiveContest());
    }

    public boolean getReturning() {
        return login.getUser().getId() != null;
    }

    // Setters -----------------------------------------------------------------------------------
}