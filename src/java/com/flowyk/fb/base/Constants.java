/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.base;

import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Lukas
 */
@Named
@Singleton
public class Constants {

    public static final boolean FINE_DEBUG = false;
    public static final boolean DEBUG = true;

    public static final String API_KEY = "675005359221214";
    public static final String SITE_URL = "https://sutaz.flowyk.com:8181/facebookContest";
//    public static final String CALLBACK_URL = "https://sutaz.flowyk.com:8181/FacebookSutaz-newwar/contest.xhtml";
    
    //ticket weight after first registration
    public static final int FIRST_REGISTRATION_TICKETS = 2;
    public static final int RETURNING_TICKETS = 2;

    public boolean isFINE_DEBUG() {
        return FINE_DEBUG;
    }

    public boolean isDEBUG() {
        return DEBUG;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

//    public String getCALLBACK_URL() {
//        return CALLBACK_URL;
//    }
    
    
}
