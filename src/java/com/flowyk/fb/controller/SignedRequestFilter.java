/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import com.flowyk.fb.exceptions.MalformedSignedRequestException;
import com.flowyk.fb.model.Login;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.stream.JsonParsingException;
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
@WebFilter(filterName = "SignedRequestFilter")
public class SignedRequestFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(SignedRequestFilter.class.getName());

    @Inject
    private SignedRequest signedRequest;

    @Inject
    Login login;

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

        if (Constants.FINE_DEBUG) {
            LOG.info(getHeaderText(req));
        }

        String signedRequestString = req.getParameter("signed_request");
        if (signedRequestString != null) {
            try {
                signedRequest.parseSignedRequest(signedRequestString);
            } catch (MalformedSignedRequestException | JsonParsingException e) {
                LOG.log(Level.INFO, "Got malformed signed request: {0}", signedRequestString);
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Malformed header");
                return;
            }
        }

        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }
        login.setIpAddress(ipAddress);

        String userAgent = req.getHeader("user-agent");
        login.setUserAgent(userAgent);

        chain.doFilter(request, response);
    }

    /**
     * DEBUG
     * @param req
     * @return 
     */
    private String getHeaderText(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder("HEADER:\n");
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            sb.append("\t").append(key).append(": ").append(req.getHeader(key)).append("\n");
        }
        sb.append("\tREMOTE ADDRESS: ").append(req.getRemoteAddr());
        return sb.toString();
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
