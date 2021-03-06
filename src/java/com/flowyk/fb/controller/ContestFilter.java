/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.model.external.OpenGraphBean;
import com.flowyk.fb.model.Login;
import com.flowyk.fb.model.signedrequest.AppData;
import com.flowyk.fb.model.signedrequest.SignedRequest;
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
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lukas
 */
@WebFilter(filterName = "ContestFilter")
public class ContestFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(ContestFilter.class.getName());

    @Inject
    private SignedRequest signedRequest;

    @Inject
    private Login login;

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
        HttpServletResponse res = (HttpServletResponse) response;
        if (signedRequest.isSigned()) {
            if (signedRequest.getPage().isLiked()) {
                if (login.getUser().getId() != null) {
                    res.sendRedirect(req.getContextPath() + "/contest/returning.xhtml");
                } else {
                    res.sendRedirect(req.getContextPath() + "/contest/register.xhtml");
                }
            } else {
                res.sendRedirect(req.getContextPath() + "/contest/presslike.xhtml");
            }
        } else {
            LOG.log(Level.INFO, "Unauthorized access: {0} {1}", new Object[]{login.getIpAddress(), login.getUserAgent()});
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "This page is accessible only through facebook");
        }

    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }
}
