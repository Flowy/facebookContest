/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.model.RulesBean;
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
@WebFilter(filterName = "FBContestRulesFilter", urlPatterns = {"/contest-rules.xhtml"})
public class ContestRulesFilter implements Filter {

    @Inject
    RulesBean rulesBean;

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
        String contestId = request.getParameter("contest");
        
        if (contestId != null) {
            rulesBean.setContestId(contestId);
            chain.doFilter(request, response);
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Unknown contest");
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
