/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.model.signedrequest.SignedRequest;
import static com.flowyk.fb.base.Constants.*;
import com.flowyk.fb.base.LoginUtil;
import com.flowyk.fb.model.ContestBean;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.json.JsonObject;
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
@WebFilter(filterName = "SignedRequestFilter", urlPatterns = {"/contest/*"})
public class SignedRequestFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(SignedRequestFilter.class.getName());

//    private static final String presslikePath = "/contest/presslike.xhtml";
//    private static final String adminPath = "/contest/admin.xhtml";
//    private static final String returningPath = "/contest/returning.xhtml";
//    private static final String thanksPath = "/contest/thanks.xhtml";
//    private static final String registerPath = "/contest/register.xhtml";
//    private static final String unactivePath = "/contest/page-unactive.xhtml";
//    private static final String contestPath = "/contest/contest.xhtml";
    
    @Inject
    private SignedRequest signedRequest;

//    @Inject
//    private ContestBean contestBean;

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
        if (FINE_DEBUG) {
            LOG.info(getHeaderText(request));
        }
        
        parseSignedRequest(request);
        
//        HttpServletRequest req = (HttpServletRequest) request;
        
        
        
//        System.out.println(req.getServletPath());
        
        chain.doFilter(request, response);

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

    private void parseSignedRequest(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String signedRequestString = request.getParameter("signed_request");
        if (signedRequestString != null) {
            JsonObject jObject = LoginUtil.parseSignedRequest(signedRequestString);
            System.out.println("Json request: " + jObject.toString());
            signedRequest.parseJsonObject(jObject);

            String ipAddress = req.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            signedRequest.setIpAddress(ipAddress);

            String userAgent = req.getHeader("user-agent");
            signedRequest.setUserAgent(userAgent);
        }
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
