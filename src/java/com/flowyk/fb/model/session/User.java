/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.session;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.RegisteredUser;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@SessionScoped
public class User implements Serializable {

    @NotNull
    private RegisteredUser registeredUser = new RegisteredUser();

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public void parseJsonObject(JsonObject jObject) {
        if (jObject == null) {
            return;
        }
        this.registeredUser.setLocale(jObject.getString("locale", null));
        this.registeredUser.setCountry(jObject.getString("country", null));
        JsonObject age = jObject.getJsonObject("age");
        if (age != null) {
            this.registeredUser.setAgeMin(age.getInt("min", 0));
            this.registeredUser.setAgeMax(age.getInt("max", 0));
        }
    }
    
    public String getShareUrl() {
        return Constants.SITE_URL
                + "/contest/contest.xhtml"
                + (registeredUser != null ? ("?reference=" + registeredUser.getId()) : "");
    }
    
}
