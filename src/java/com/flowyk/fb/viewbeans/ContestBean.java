/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.viewbeans;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.auth.FacebookLogin;
import com.flowyk.fb.sigrequest.SignedRequest;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
@Named
@ViewScoped
public class ContestBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(ContestBean.class.getName());

    @Inject
    FacebookLogin login;

    @PersistenceContext(unitName = "fbContestDB")
    EntityManager em;

    private Contest active = null;

    /**
     * Creates a new instance of ContestBean
     */
    public ContestBean() {
    }

    // Actions -----------------------------------------------------------------------------------
    /**
     *
     * @param list
     * @return returns active contest or null if no active found
     */
    private Contest selectActiveContest(List<Contest> list) {
        Collections.sort(list, Collections.reverseOrder());
        Contest selected = null;
        Date now = new Date();
        for (Contest x : list) {
            if (!x.isDisabled() && x.getContestEnd().after(now)) {
                selected = x;
            }
        }
        return selected;
    }

    // Getters -----------------------------------------------------------------------------------
    /**
     *
     * @return null if not active signed request or page does not have contest
     */
    public Contest getActiveContest() {
        if (active == null && login != null && login.getSignedRequest() != null) {
            List<Contest> list = (List<Contest>) em.createNamedQuery("Contest.findByPage")
                    .setParameter("page", login.getSignedRequest().getPageId()).getResultList();
            active = selectActiveContest(list);
        }
        return active;
    }

    /**
     *
     * @param page with starting slash
     * @return
     */
    public String getPageUrl(String page) {
        Contest contest = getActiveContest();
        if (contest != null) {
            return "/WEB-INF/contest/layouts/" + contest.getLayout().getName() + page;
        } else {
            return null;
        }
    }

    /**
     *
     * @param resource with starting slash
     * @return
     */
    public String getResourceUrl(String resource) {
        Contest contest = getActiveContest();
        if (contest != null) {
            return "./contest/layouts/" + contest.getLayout().getName() + resource;
        } else {
            return null;
        }
    }

    /**
     *
     * @param image with starting slash
     * @return
     */
    public String getImageUrl(String image) {
        if (login.getSignedRequest() != null) {
            return "/images/" + login.getSignedRequest().getPageId() + image;
        } else {
            return null;
        }
    }

    public String getContestPage() {
        SignedRequest req = login.getSignedRequest();
        if (req != null) {
            String page;
            if (req.isPageLike()) {
                page = "/register.xhtml";
            } else {
                page = "/presslike.xhtml";
            }
            return getPageUrl(page);
        } else {
            LOG.severe("I got to contest page without signed request!!!");
            return null;
        }
    }

    // Setters -----------------------------------------------------------------------------------
}
