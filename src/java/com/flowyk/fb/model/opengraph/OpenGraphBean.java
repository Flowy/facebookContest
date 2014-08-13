/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.opengraph;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.model.ShareUrlBean;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;
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

    private RegisteredUser user = null;
    private Contest contest = null;

    @Inject
    SignedRequest signedRequest;

    @Inject
    ShareUrlBean shareUrlBean;
    
    @EJB
    RegisteredUserFacade registeredUserFacade;

    @EJB
    ContestFacade contestFacade;

    @PostConstruct
    public void init() {
        int userId = signedRequest.getAppData().getReference();
        if (userId != 0) {
            user = registeredUserFacade.find(userId);
            if (user != null) {
                contest = user.getContest();
            }
            if (Constants.FINE_DEBUG) {
                LOG.log(Level.INFO, "Found user: {0} for reference id: {1}", new Object[]{user, userId});
            }
        }

        Integer contestId = signedRequest.getContestId();
        if (contestId != null) {
            contest = contestFacade.find(contestId);
        }
    }

    // Actions -----------------------------------------------------------------------------------
    // Getters -----------------------------------------------------------------------------------
    public String getTitle() {
        if (contest != null) {
            return contest.getName();
        } else {
            return "Contest";
        }
    }

    public String getImage() {
        if (contest != null) {
            return contest.getShareImgUrl();
        } else {
            return Constants.SITE_URL + "/images/default_share_img.png";
        }
    }

    public String getDescription() {
        if (contest != null) {
            return contest.getPopisSutaze();
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
        if (contest != null) {
            StringBuilder sb;
            sb = new StringBuilder("https://www.facebook.com/")
                    .append(contest.getRegisteredPage().getPageId())
                    .append("?sk=app_")
                    .append(Constants.API_KEY);
            return sb.toString();
        } else {
            return "https://apps.facebook.com/flowykcontests";
        }
    }

    public String getFBShareUrl() {
        return shareUrlBean.getFBShareUrl(user);
    }

    // Setters -----------------------------------------------------------------------------------
}
