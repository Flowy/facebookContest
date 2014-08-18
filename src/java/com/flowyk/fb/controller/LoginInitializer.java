/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.exceptions.NoActiveContestException;
import com.flowyk.fb.model.Login;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@WebFilter(filterName = "LoginInitializer")
public class LoginInitializer implements Filter {

    private static final Logger LOG = Logger.getLogger(LoginInitializer.class.getName());

    @Inject
    Login login;

    @Inject
    SignedRequest signedRequest;

    @EJB
    RegisteredPageFacade registeredPageFacade;

    @EJB
    ContestFacade contestFacade;

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        String pageId = signedRequest.getPage().getPageId();
        if (pageId != null) {
            setPage(pageId);
            chain.doFilter(request, response);
        } else {
            LOG.severe("Page id not found");
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.responseSendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No page id found"
            );
        }

    }

    /**
     * creates new if page does not exists
     *
     * @param pageId
     * @throws com.flowyk.fb.exceptions.NoActiveContestException
     * @throws NullPointerException if pageId parameter is null
     */
    private void setPage(@NotNull String pageId) throws IOException {

        RegisteredPage page = registeredPageFacade.find(pageId);
        if (page == null) {
            page = createRecordsForPage(pageId);
        }
        login.setPage(page);
    }

    private RegisteredPage createRecordsForPage(String pageId) throws IOException {
        Path pagePath = Constants.LOCAL_IMAGES_PATH.resolve(pageId);
        try {
            Files.createDirectories(pagePath);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.responseSendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can not create directory in file system for page, please try again later");
        }
        RegisteredPage p = new RegisteredPage(pageId, new Date());
        registeredPageFacade.create(p);
        return p;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

}
