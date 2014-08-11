/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.model.opengraph.OpenGraphBean;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.IOException;
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

    @Inject
    SignedRequest signedRequest;

    @Inject
    OpenGraphBean openGraphBean;
    
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
        if (userAgent != null && userAgent.contains("facebookexternalhit")) {
            //facebook bot - allow to see open graph page
            chain.doFilter(request, response);
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            if (signedRequest.isSigned()) {
                if (signedRequest.getPage().isLiked()) {
                    //TODO: redirect to fb page if not on already (signed request should not be session scoped)
                    res.sendRedirect(req.getContextPath() + "/contest/register.xhtml");
                } else {
                    res.sendRedirect(req.getContextPath() + "/contest/presslike.xhtml");
                }
            } else {
                res.sendRedirect(openGraphBean.getFBAddress());
            }
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }
}
