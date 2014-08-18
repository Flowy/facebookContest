/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.model.session.ContestBean;
import com.flowyk.fb.model.opengraph.OpenGraphBean;
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
    SignedRequest signedRequest;

    @Inject
    private OpenGraphBean openGraphBean;

    @Inject
    ContestBean contestBean;

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
        String userAgent = req.getHeader("user-agent");
//        if (userAgent != null && userAgent.contains("facebookexternalhit")) {
            //facebook bot - allow to see open graph page

        String contest = req.getParameter("contest");
        if (contest != null) {
            try {
                Integer.valueOf(contest);
                int contestInt = Integer.parseInt(contest);
                openGraphBean.setContestId(contestInt);
            } catch (NumberFormatException e) {
                LOG.log(Level.INFO, "Can't parse contest id from: {0}", contest);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Malformed header");
                return;
            }
        } else {
            
        }
            chain.doFilter(request, response);
//        } else {
//            HttpServletResponse res = (HttpServletResponse) response;
//            handleRequest(req, res);
//        }
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (signedRequest.isSigned()) {
            if (signedRequest.getPage().isLiked()) {
                if (contestBean.getReturning()) {
                    res.sendRedirect(req.getContextPath() + "/contest/returning.xhtml");
                } else {
                    res.sendRedirect(req.getContextPath() + "/contest/register.xhtml");
                }
            } else {
                res.sendRedirect(req.getContextPath() + "/contest/presslike.xhtml");
            }
        } else {
            res.sendRedirect(openGraphBean.getFBShareUrl());
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }
}
