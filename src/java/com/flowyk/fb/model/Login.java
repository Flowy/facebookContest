/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.exceptions.NoActiveContestException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@Named
@SessionScoped
public class Login implements Serializable {

    private static final Logger LOG = Logger.getLogger(Login.class.getName());

    @EJB
    RegisteredUserFacade registeredUserFacade;

    @EJB
    RegisteredPageFacade registeredPageFacade;

    @EJB
    ContestFacade contestFacade;

    private Contest contest;
    private RegisteredPage page;
    private RegisteredUser user;

    private String ipAddress;
    private String userAgent;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public RegisteredPage getPage() {
        return page;
    }

    /**
     * actualizes reference to page in there, in user, and sets actual contest
     * 
     * @param page 
     */
    public void setPage(@NotNull RegisteredPage page) {
        this.page = page;
        this.contest = getActualContest(page);
        this.user = new RegisteredUser();
        this.user.setContest(this.contest);
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(@NotNull Contest contest) {
        this.page = contest.getRegisteredPage();
        this.contest = contest;
        user.setContest(contest);
    }

    public RegisteredUser getUser() {
        return user;
    }
    
    /**
     * rewrites user with associated contest and page
     * @param user 
     */
    public void setUser(@NotNull RegisteredUser user) {
        this.user = user;
        this.contest = user.getContest();
        if (this.contest != null) {
            this.page = this.contest.getRegisteredPage();
        }
    }

    /**
     *
     * @param page
     * @return active + actual contest for given page
     * @throws NoActiveContestException if no active/actual contest found
     */
    private Contest getActualContest(RegisteredPage page) {
        List<Contest> contestList = contestFacade.findByPage(page);
        if (contestList.isEmpty()) {
            return null;
        } else {
            return selectActualContest(contestList);
        }
    }

    /**
     *
     * @param list
     * @return null if no active+actual contest
     */
    private static Contest selectActualContest(@NotNull List<Contest> list) {
        Collections.sort(list, Collections.reverseOrder());
        Contest selected = null;
        Date now = new Date();
        for (Contest x : list) {
            if (now.before(x.getContestEnd())) {
                selected = x;
            }
        }
        return selected;
    }
}
