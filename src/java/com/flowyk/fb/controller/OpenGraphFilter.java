/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.model.external.OpenGraphBean;
import com.flowyk.fb.model.signedrequest.AppData;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Lukas
 */
@WebFilter(filterName = "OpenGraphFilter")
public class OpenGraphFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(OpenGraphFilter.class.getName());

    @Inject
    private OpenGraphBean openGraphBean;

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
        HttpServletRequest req = (HttpServletRequest) request;

        String appDataString = req.getParameter("app_data");
        String contestString = req.getParameter("contest");
        if (appDataString != null) {
            AppData appData = AppData.parseString(appDataString);
            openGraphBean.setReferenceId(appData.getReference());
        } else if (contestString != null) {
            Integer contestId = null;
            try {
                contestId = Integer.parseInt(contestString);
            } catch (NumberFormatException e) {
                LOG.log(Level.INFO, "Can not parse contest id to integer: {0}", contestString);
            }
            openGraphBean.setContestId(contestId);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }
}
