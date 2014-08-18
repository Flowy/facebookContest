/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model.external;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.facade.ContestFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Lukas
 */
@Named
@RequestScoped
public class RulesBean implements Serializable {

    private Contest contest;
    
    @EJB
    private ContestFacade contestFacade;

    // Actions -----------------------------------------------------------------------------------



    // Getters -----------------------------------------------------------------------------------
    
    public String getRulesText() {
        if (contest != null) {
            return contest.getRules();
        } else {
            throw new IllegalStateException("Trying to access rules without setting contest");
        }
    }

    // Setters -----------------------------------------------------------------------------------

    public void setContestId(Integer contestId) {
        if (contestId != null) {
            contest = contestFacade.find(contestId);
        }
    }
}
