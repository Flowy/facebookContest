/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.model.session.AppData;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Lukas
 */
@Named(value="shareUrlBean")
@RequestScoped
public class ShareUrlBean {

    public String getFBShareUrl(RegisteredUser user) {
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
    
}
