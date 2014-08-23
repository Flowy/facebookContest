/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.model.signedrequest.AppData;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Lukas
 */
@Named
@RequestScoped
public class ShareUrlBean {

    @Inject
    Login login;

    public String getFBShareUrl() {
        RegisteredUser user = login.getUser();
        if (user != null) {
            AppData appData = new AppData();
            appData.setReference(user.getId());
            StringBuilder sb;
            sb = new StringBuilder("https://www.facebook.com/")
                    .append(user.getContest().getRegisteredPage().getPageId())
                    .append("?sk=app_")
                    .append(Constants.API_KEY)
                    .append("&app_data=")
                    .append(appData.getAsHeader());
            return sb.toString();
        } else {
            return "https://apps.facebook.com/flowykcontests";
        }
    }

    public String getShareUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SITE_URL);
        sb.append("/contest/contest.xhtml");

        RegisteredUser reference = login.getUser();
        if (reference != null) {
            AppData appData = new AppData();
            appData.setReference(reference.getId());
            sb.append("?").append(appData.getAsHeader());
        }
        return sb.toString();
    }

}
