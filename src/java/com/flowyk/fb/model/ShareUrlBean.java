/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.model.session.Login;
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
    
    public String getFBShareUrl(RegisteredUser user) {
//        if (user != null) {
//            AppData appData = new AppData();
//            appData.setReference(user.getId());
//            StringBuilder sb;
//            sb = new StringBuilder("https://www.facebook.com/")
//                    .append(user.getContest().getRegisteredPage().getPageId())
//                    .append("?sk=app_")
//                    .append(Constants.API_KEY)
//                    .append("&app_data=")
//                    .append(appData.getAsJson().toString());
//            return sb.toString();
//        } else {
            return "https://apps.facebook.com/flowykcontests";
//        }
    }
    
    public String getShareUrl() {
        return Constants.SITE_URL
                + "/contest/contest.xhtml"
                + (login.getUser() != null ? ("?reference=" + login.getUser().getId()) : "");
    }
    
}
