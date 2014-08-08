/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.auth;

import static com.flowyk.fb.base.Constants.*;
import java.io.IOException;
import java.util.Enumeration;
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
@WebFilter(filterName = "FBLoginFilter", urlPatterns = {"/contest.xhtml", "/contest-thanks.xhtml"})
public class FacebookLoginFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(FacebookLoginFilter.class.getName());

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
        HttpServletRequest req = (HttpServletRequest) request;

        if (FINE_DEBUG) {
            LOG.info(getHeaderText(request));
        }

        String signedRequestString = request.getParameter("signed_request");
        if (signedRequestString != null) {
            login.parseSignedRequest(signedRequestString);
            
            String ipAddress = req.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            login.getSignedRequest().setIpAddress(ipAddress);

            String userAgent = req.getHeader("user-agent");
            login.getSignedRequest().setUserAgent(userAgent);
            
        }
        if (login.getSignedRequest() != null) {
            chain.doFilter(request, response);
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

    private String getHeaderText(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        StringBuilder sb = new StringBuilder("HEADER:\n");
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            sb.append("\t").append(key).append(": ").append(req.getHeader(key)).append("\n");
        }
        sb.append("\tREMOTE ADDRESS: ").append(request.getRemoteAddr());
        return sb.toString();

    }

}
