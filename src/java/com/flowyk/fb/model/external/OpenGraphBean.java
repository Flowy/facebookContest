/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.external;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.model.ContestBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Lukas
 */
@Named(value = "openGraphBean")
@RequestScoped
public class OpenGraphBean {

    private static final Logger LOG = Logger.getLogger(OpenGraphBean.class.getName());

    @Inject
    private ContestBean contestBean;

    @EJB
    private RegisteredUserFacade registeredUserFacade;

    @EJB
    private ContestFacade contestFacade;

    private Contest contest = null;

    // Actions -----------------------------------------------------------------------------------
    // Getters -----------------------------------------------------------------------------------
    public void setReferenceId(Integer referenceId) {
        if (referenceId != null) {
            RegisteredUser user = registeredUserFacade.find(referenceId);
            if (user != null) {
                contest = user.getContest();
            }
        }
    }

    public void setContestId(Integer contestId) {
        if (contestId != null) {
            contest = contestFacade.find(contestId);
        }
    }

    public String getTitle() {
        if (contest != null) {
            return contest.getName();
        } else {
            return "Contest";
        }
    }

    public String getImage() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SITE_URL);
        if (contest != null) {
            sb.append(contestBean.getShareImgUrl("share-img.png", contest));
        } else {
            sb.append("/images/default_share_img.png");
        }
        return sb.toString();
    }

    public String getDescription() {
        if (contest != null) {
            return contest.getDescription();
        } else {
            return null;
        }
    }

    public String getContestEnd() {
        if (contest != null) {
            TimeZone tz = TimeZone.getDefault();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            df.setTimeZone(tz);
            String date = df.format(contest.getContestEnd());
            return date;
        } else {
            return null;
        }
    }

    public String getCanonicalUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SITE_URL);
        sb.append("/contest/contest.xhtml");
        if (contest != null) {
            sb.append("?contest=").append(contest.getId());
        }
        return sb.toString();
    }
}
