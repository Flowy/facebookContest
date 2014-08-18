/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.opengraph;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.facade.custom.CustomContestFacade;
import com.flowyk.fb.entity.facade.custom.CustomRegisteredUserFacade;
import com.flowyk.fb.model.session.ContestBean;
import com.flowyk.fb.model.session.Login;
import com.flowyk.fb.model.ShareUrlBean;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
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
    private ContestBean contestBean;

    @Inject
    private Login login;

    @Inject
    private SignedRequest signedRequest;


    @Inject
    private ShareUrlBean shareUrlBean;

    @EJB
    private CustomRegisteredUserFacade registeredUserFacade;

    @EJB
    private CustomContestFacade contestFacade;

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
    
    private int contestId;
    
    public void setContestId(int contestId) {
        System.out.println("Setting contest by header: " + contestId);
        this.contestId = contestId;
    }
    
    public String getTitle() {
        LOG.severe("Found managed property contest ID: " + contestId);
//        if (contest != null) {
//            return contest.getName();
//        } else {
            return "Contest";
//        }
    }

    public String getImage() {
//        if (contest != null) {
//            return contestBean.getImageUrl("share-img.png", contest);
//        } else {
            return Constants.SITE_URL + "/images/default_share_img.png";
//        }
    }

    public String getDescription() {
//        if (contest != null) {
//            return contest.getDescription();
//        } else {
            return null;
//        }
    }

    public String getContestEnd() {
//        if (contest != null) {
//            TimeZone tz = TimeZone.getDefault();
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
//            df.setTimeZone(tz);
//            String date = df.format(contest.getContestEnd());
//            return date;
//        } else {
            return null;
//        }
    }

    public String getCanonicalUrl() {
//        if (contest != null) {
//            StringBuilder sb;
//            sb = new StringBuilder("https://www.facebook.com/")
//                    .append(contest.getRegisteredPage().getPageId())
//                    .append("?sk=app_")
//                    .append(Constants.API_KEY);
//            return sb.toString();
//        } else {
            return "https://apps.facebook.com/flowykcontests";
//        }
    }

    public String getFBShareUrl() {
        
//        return shareUrlBean.getFBShareUrl(user);
        return null;
    }
}
