/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.session;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.entity.facade.custom.CustomContestFacade;
import com.flowyk.fb.exceptions.PageIdNotFoundException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@SessionScoped
public class Login implements Serializable {

    @EJB
    private CustomContestFacade contestFacade;
    
    @EJB
    RegisteredPageFacade registeredPageFacade;
    
    private RegisteredPage page = null;
    private RegisteredUser user = null;
    
    private String ipAddress;
    private String userAgent;
    private Contest actualContest = null;

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

    public Contest getActualContest() {
        return actualContest;
    }

    public void setActualContest(Contest actualContest) {
        this.actualContest = actualContest;
    }

    public RegisteredPage getPage() {
        return page;
    }

    public RegisteredUser getUser() {
        return user;
    }
    
    /**
     * creates new if page does not exists
     * @param pageId 
     * @throws NullPointerException if pageId is null
     */
    public void setPage(@NotNull String pageId){
        if (page == null || !page.getPageId().equals(pageId)) {
            RegisteredPage regPage = registeredPageFacade.find(pageId);
            if (regPage != null) {
                this.page = regPage;
            } else {
                //TODO create new entry if this page does not exists yet
                throw new PageIdNotFoundException();
            }
        }
    }
}
