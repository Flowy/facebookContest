/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.viewbeans;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.oauth.FacebookLogin;
import com.flowyk.fb.sigrequest.SignedRequest;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
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
    public Contest getActiveContest() {
        if (active == null) {
            active = getActiveContestForPage(login.getSignedRequest().getPageId());
        }
        return active;
    }
    
    public Contest getActiveContestForPage(String page) {
        if (active == null) {
            List<Contest> list = (List<Contest>) em.createNamedQuery("Contest.findByPage")
                    .setParameter("page", page).getResultList();
            active = selectActiveContest(list);
        }
        return active;
    }
    
    public String getPage() {
        SignedRequest req = login.getSignedRequest();
        String result = null;
        if (req != null) {
            if (req.isPageLike()) {
                result = "register";
            } else {
                result = "presslike";
            } 
        //TODO: implement admin
        } else {
            LOG.severe("I got to contest page without signed request!!!");
        }
        return result;
    }

    // Setters -----------------------------------------------------------------------------------
}
