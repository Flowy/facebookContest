/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.exceptions.NoActiveContestException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
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

    private RegisteredUser user = new RegisteredUser();

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
        return user
                .getContest()
                .getRegisteredPage();
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(@NotNull RegisteredUser user) {
        this.user = user;
    }

    /**
     * creates new if page does not exists
     *
     * @param pageId
     * @throws NullPointerException if pageId is null
     */
    public void setPage(@NotNull String pageId) {
        if (user.getContest() == null) {
            RegisteredPage page = registeredPageFacade.find(pageId);
            if (page != null) {
                Contest contest = getActualContest(page);
                if (contest != null) {
                    user.setContest(contest);
                } else {
                    throw new NoActiveContestException("for page: " + pageId);
                }
            } else {
                //TODO create new entry if this page does not exists yet
                throw new IllegalArgumentException("Page with page id: " + pageId + " not found");
            }
        } else {
            if (!pageId.equals(user.getContest().getRegisteredPage().getPageId())) {
                user = new RegisteredUser();
                LOG.log(Level.INFO, "User {0}\t\nfrom another contest {1}\t\ncame to this page {2}", new Object[]{user, user.getContest(), pageId});
            }
        }
    }

    private Contest getActualContest(RegisteredPage page) {
        List<Contest> contestList = contestFacade.findByPage(page);
        if (contestList.isEmpty()) {
            System.out.println("Contest list is: " + contestList);
            return null;
        } else {
            return selectActiveContest(contestList);
        }
    }

    private static Contest selectActiveContest(@NotNull List<Contest> list) {
        Collections.sort(list, Collections.reverseOrder());
        Contest selected = null;
        Date now = new Date();
        for (Contest x : list) {
            if (!x.getDisabled() && now.before(x.getContestEnd())) {
                selected = x;
            }
        }
        if (selected != null) {
            return selected;
        } else {
            throw new NoActiveContestException();
        }
    }
}
