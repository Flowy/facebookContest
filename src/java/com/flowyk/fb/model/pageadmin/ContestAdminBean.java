/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model.pageadmin;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.entity.facade.RegistrationFacade;
import javax.inject.Named;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Lukas
 */
@Named
@RequestScoped
public class ContestAdminBean implements Serializable{


    @EJB
    RegisteredUserFacade registeredUserFacade;
    
    @EJB
    RegistrationFacade registrationFacade;
    
    // Actions -----------------------------------------------------------------------------------


    // Getters -----------------------------------------------------------------------------------
    
    public String getContestUserCount(Contest contest) {
        return String.valueOf(registeredUserFacade.findByContest(contest).size());
    }
    
    public String getContestTicketsCount(Contest contest) {
        return String.valueOf(registrationFacade.getActiveByContest(contest).size());
    }

    // Setters -----------------------------------------------------------------------------------

}
