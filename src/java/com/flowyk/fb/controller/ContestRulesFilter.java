/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.controller;

import com.flowyk.fb.model.external.RulesBean;
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
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lukas
 */
@WebFilter(filterName = "FBContestRulesFilter", urlPatterns = {"/contest-rules.xhtml"})
public class ContestRulesFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(ContestRulesFilter.class.getName());

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

        String contestString = request.getParameter("contest");

        if (contestString != null) {
            try {
                int contestInt = Integer.parseInt(contestString);
                rulesBean.setContestId(contestInt);
                chain.doFilter(request, response);
            } catch (NumberFormatException e) {
                LOG.log(Level.INFO, "Can't parse contest id from: {0}", contestString);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Malformed header");
            }
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Unknown contest");
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
