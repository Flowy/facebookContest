/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import java.io.IOException;
import java.util.logging.Logger;
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
@WebFilter(filterName = "ContestOGRedirect")
public class ContestOGRedirect implements Filter {

    private static final Logger LOG = Logger.getLogger(ContestOGRedirect.class.getName());

    
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
        
        String userAgent = req.getHeader("user-agent");
        //facebook bot - redirect to open graph
        if (userAgent != null && userAgent.contains("facebookexternalhit")) {
            System.out.println("Context path: " + req.getContextPath());
            String redirectUrl = req.getContextPath() + "/opengraph.xhtml?";
            redirectUrl += req.getQueryString();
            System.out.println("redirecting to: " + redirectUrl);
            res.sendRedirect(redirectUrl);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }
}
