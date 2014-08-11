/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.opengraph;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.model.signedrequest.AppData;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.inject.Inject;

/**
 *
 * @author Lukas
 */
@Named(value = "openGraphBean")
@RequestScoped
public class OpenGraphBean {

    private RegisteredUser user;
    private static final Logger LOG = Logger.getLogger(OpenGraphBean.class.getName());

    @Inject
    SignedRequest signedRequest;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        int id = signedRequest.getAppData().getReference();
        user = em.find(RegisteredUser.class, id);
        System.out.println("OpenGraphBean Found user: " + user + " for reference id: " + id);
    }

    // Actions -----------------------------------------------------------------------------------
    // Getters -----------------------------------------------------------------------------------
    public String getTitle() {
        if (user != null) {
            return user.getContest().getName();
        } else {
            return "Contests";
        }
    }

    public String getImage() {
        if (user != null) {
            return user.getContest().getIconUrl();
        } else {
            return null;
        }
    }

    public String getDescription() {
        if (user != null) {
            return user.getContest().getPopisSutaze();
        } else {
            return null;
        }
    }

    public String getContestEnd() {
        if (user != null) {
            TimeZone tz = TimeZone.getDefault();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            df.setTimeZone(tz);
            String date = df.format(user.getContest().getContestEnd());
            return date;
        } else {
            return null;
        }
    }

    public String getCanonicalUrl() {
        if (user != null) {
            StringBuilder sb;
            sb = new StringBuilder("https://www.facebook.com/")
                    .append(user.getContest().getRegisteredPage().getPageId())
                    .append("?sk=app_")
                    .append(Constants.API_KEY);
            return sb.toString();
        } else {
            return "https://apps.facebook.com/flowykcontests";
        }
    }

    public String getFBAddress() {
        if (user != null) {
            AppData appData = new AppData();
            appData.setReference(user.getId());
            StringBuilder sb;
            sb = new StringBuilder("https://www.facebook.com/")
                    .append(user.getContest().getRegisteredPage().getPageId())
                    .append("?sk=app_")
                    .append(Constants.API_KEY)
                    .append("&app_data=")
                    .append(appData.getAsJson().toString());
            return sb.toString();
        } else {
            return "https://apps.facebook.com/flowykcontests";
        }
    }

    // Setters -----------------------------------------------------------------------------------
}
