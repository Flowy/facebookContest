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
import com.flowyk.fb.exception.FBPageNotActiveException;
import com.flowyk.fb.exception.NoActiveContestException;
import com.flowyk.fb.exception.PageIdNotFoundException;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

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

    @PersistenceContext(unitName = "fbContestDB")
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    private Boolean returning = false;


    // Actions -----------------------------------------------------------------------------------
    
    /**
     * expects open transaction and persisted user
     */
    void createNewTicket(RegisteredUser forUser) {
        Registration ticket = new Registration();
        ticket.setRegisteredUser(forUser);
        ticket.setTimeRegistered(new Date());
        ticket.setIpAddress(signedRequest.getIpAddress());
        ticket.setUserAgent(signedRequest.getUserAgent());
        em.persist(ticket);
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
            RegisteredPage page = em.find(RegisteredPage.class, signedRequest.getPage().getId());
            if (page != null) {
                List<Contest> list = em.createNamedQuery("Contest.findByRegisteredPage").setParameter("registeredPage", page).getResultList();
//                List<Contest> list = new ArrayList(page.getContestCollection());
                return selectActiveContest(list);
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
            RegisteredPage page = em.find(RegisteredPage.class, pageId);
            if (page != null && page.getActive()) {
                return true;
            }
        }
        return false;
    }

    public Boolean isReturning() {
        return returning;
    }

    // Setters -----------------------------------------------------------------------------------
    
    public void setReturning(Boolean value) {
        this.returning = value;
    }
}
