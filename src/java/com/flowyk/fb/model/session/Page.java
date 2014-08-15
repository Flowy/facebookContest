/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.session;

import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.exceptions.PageIdNotFoundException;
import com.flowyk.fb.exceptions.MalformedSignedRequestException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@Named
@SessionScoped
public class Page implements Serializable {

    @EJB
    RegisteredPageFacade registeredPageFacade;

    private RegisteredPage page = null;

    private boolean liked;
    private boolean admin;

    public RegisteredPage getPage() {
        return page;
    }

    public void setPage(String pageId) {
        if (pageId == null) {
            throw new IllegalArgumentException("Page id is null");
        }
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

    public void setPage(RegisteredPage page) {
        this.page = page;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void parseJsonObject(@NotNull JsonObject jObject) {
        String pageId;
        try {
            pageId = jObject.getString("id");
        } catch (NullPointerException e) {
            throw new MalformedSignedRequestException("Page id is null");
        }
        setPage(pageId);
        this.liked = jObject.getBoolean("liked", false);
        this.admin = jObject.getBoolean("admin", false);
    }
}
