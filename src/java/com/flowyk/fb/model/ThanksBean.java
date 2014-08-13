/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Lukas
 */
@Named(value = "thanksBean")
@RequestScoped
public class ThanksBean implements Serializable {

    @Inject
    private SignedRequest signedRequest;
    
    public String getShareScript() {
        Integer userId = signedRequest.getUser().getId();
        StringBuilder sb = new StringBuilder("FB.ui({");
        sb.append("method: 'share_open_graph',");
        sb.append("action_type: 'flowykcontests:attend',");
        sb.append("action_properties: {")
                .append("contest: '").append(Constants.SITE_URL).append("/contest/contest.xhtml?reference=").append(userId).append("'");
        sb.append("} }); return false;");
        return sb.toString();
    }
    
}
