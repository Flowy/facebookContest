/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model;

import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lukas
 */
@Named(value = "thanksBean")
@ViewScoped
public class ThanksBean {

    private String userId;
    
    public String getShareScript() {
        StringBuilder sb = new StringBuilder("FB.ui({");
        sb.append("method: 'share_open_graph',");
        sb.append("action_type: 'flowykcontests:attend',");
        sb.append("action_properties: {");
        sb.append("contest: 'https://sutaz.flowyk.com:8181/facebookContest/contest/contest.xhtml?reference=").append(userId).append("'");
        sb.append("} }); return false;");
        return sb.toString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
}
