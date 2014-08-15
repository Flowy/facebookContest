/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.session;

import com.flowyk.fb.model.session.Page;
import com.flowyk.fb.model.session.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
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

    private boolean signed;

    @Inject
    private Page page;
    @Inject
    private User user;
    
    private final AppData appData = new AppData();

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void parseSignedRequestJson(JsonObject json) {
        if (json == null) {
            return;
        }
        JsonObject pageObject = json.getJsonObject("page");
        page.parseJsonObject(pageObject);

        JsonObject userObject = json.getJsonObject("user");
        user.parseJsonObject(userObject);

        String appDataString = json.getString("app_data", null);
        try {
            appData.parseJson(json.getJsonObject(appDataString));
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
