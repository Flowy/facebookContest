/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.signedrequest;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.JsonObject;

/**
 *
 * @author Lukas
 */
@Named
@SessionScoped
public class SignedRequest implements Serializable {

    private static final Logger LOG = Logger.getLogger(SignedRequest.class.getName());

    private String ipAddress;
    private String userAgent;

    private Page page = new Page();
    private User user = new User();
    private AppData appData = new AppData();

    public AppData getAppData() {
        return appData;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void parseJsonObject(JsonObject json) {
        if (json == null) {
            return;
        }
        JsonObject pageObject = json.getJsonObject("page");
        this.page.parseJsonObject(pageObject);

        JsonObject userObject = json.getJsonObject("user");
        this.user.parseJsonObject(userObject);

        String appDataString = json.getString("app_data", null);
        try {
            this.appData.parseJson(json.getJsonObject(appDataString));
        } catch (ClassCastException e) {
            LOG.log(Level.WARNING, "Can't parse app data parameter: " + appDataString, e);
        }

        //Available only for users that have token in this app
//        this.userId = json.getString("user_id", null);
//        this.code = json.getString("code", null);
//        this.algorithm = json.getString("algorithm", null);
//        this.oauthToken = json.getString("oauth_token", null);
//        JsonNumber issuedTemp = json.getJsonNumber("issued_at");
//        if (issuedTemp != null) {
//            result.issuedAt = issuedTemp.longValue();
//        }
//        JsonNumber expiresTemp = json.getJsonNumber("expires");
//        if (expiresTemp != null) {
//            result.expires = expiresTemp.longValue();
//        }
    }
}
