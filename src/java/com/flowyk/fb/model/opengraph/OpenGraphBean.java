/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.opengraph;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.custom.CustomContestFacade;
import com.flowyk.fb.entity.facade.custom.CustomRegisteredUserFacade;
import com.flowyk.fb.model.session.ContestBean;
import com.flowyk.fb.model.session.Login;
import com.flowyk.fb.model.ShareUrlBean;
import com.flowyk.fb.model.session.AppData;
import com.flowyk.fb.model.session.SignedRequest;
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

//    private RegisteredUser user = null;
//    private Contest contest = null;

    @Inject
    ContestBean contestBean;

    @Inject
    Login login;

    @Inject
    SignedRequest signedRequest;

    @Inject
    private AppData appDataBean;

    @Inject
    ShareUrlBean shareUrlBean;

    @EJB
    CustomRegisteredUserFacade registeredUserFacade;

    @EJB
    CustomContestFacade contestFacade;

//    @PostConstruct
//    public void init() {
//        if (appDataBean.getReference() != null) {
//            contest = appDataBean.getReference().getContest();
//        }
//
//        int contestId = login.getContestId();
//        if (contestId != 0) {
//            contest = contestFacade.find(contestId);
//        }
//    }

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
            return contestBean.getImageUrl("share-img.png", contest);
        } else {
            return Constants.SITE_URL + "/images/default_share_img.png";
        }
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
}
