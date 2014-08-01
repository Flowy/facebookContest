/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.oauth;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lukas
 */
@WebFilter(filterName = "FBLoginFilter", urlPatterns = {"/contest.xhtml", "/contest-thanks.xhtml"})
public class FacebookLoginFilter implements Filter {

    @Inject
    FacebookLogin login;

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
        HttpServletResponse res = (HttpServletResponse) response;
//        System.out.println("Parsing signature");

        String signedRequestString = request.getParameter("signed_request");
        if (signedRequestString != null) {
            login.parseSignedRequest(signedRequestString);
        }
        if (login.getSignedRequest() != null) {
//            System.out.println("Found request: {" + login.getSignedRequest() + "}");
            chain.doFilter(request, response);
//            System.out.println("Chain do filter successful");
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Page accessible only through facebook");
        }

////        System.out.println("Filter got request from: " + req.getRequestURL() + "\nparameters: " + req.getQueryString());
//        String code = request.getParameter("code");
//        if (code != null) {
//            login.setCode(code);
//        }
//        
////        String contestUrl = req.getContextPath() + "/faces/contest.xhtml";
//        String errorUrl = req.getContextPath() + "/faces/error.xhtml";
//
////        if (req.getRequestURI().equals(contestUrl)) {
//        if (login.checkLogin()) {
//            chain.doFilter(request, response);
//        } else {
//            res.sendRedirect(errorUrl);
//        }
////        } else {
////            chain.doFilter(request, response);
////        }
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

}
