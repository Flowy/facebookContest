/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.opengraph;

import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.model.signedrequest.AppData;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static com.flowyk.fb.base.Constants.API_KEY;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 *
 * @author Lukas
 */
@Named(value = "openGraphBean")
@RequestScoped
public class OpenGraphBean {

    private RegisteredUser user;
    private static final Logger LOG = Logger.getLogger(OpenGraphBean.class.getName());

    @PersistenceContext
    EntityManager em;

    /**
     * Creates a new instance of OpenGraphBean
     */
    public OpenGraphBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            Map<String, String> parameterMap = (Map<String, String>) facesContext.getExternalContext().getRequestParameterMap();
            String userIdString = parameterMap.get("id");
            init(userIdString);
        } else {
            user = null;
        }
    }

    public void init(String userIdString) {

        try {
            Integer userId = Integer.valueOf(userIdString);
            user = em.find(RegisteredUser.class, userId);
        } catch (NumberFormatException e) {
            LOG.log(Level.WARNING, "Can't parse user ID to Integer; ID: {0}", userIdString);
            user = null;
        }
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

    public String getFBAddress() {
        if (user != null) {
            AppData appData = new AppData();
            appData.setReference(user.getId());
            StringBuilder sb;
            sb = new StringBuilder("https://www.facebook.com/")
                    .append(user.getContest().getRegisteredPage().getPageId())
                    .append("?v=app_")
                    .append(API_KEY)
                    .append("&app_data=")
                    .append(appData.getAsJson().toString());
            return sb.toString();
        } else {
            return "https://www.facebook.com";
        }
    }

    
    // Setters -----------------------------------------------------------------------------------
}
