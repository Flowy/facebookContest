/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.viewbeans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Lukas
 */
@Named(value="rulesBean")
@SessionScoped
public class RulesBean implements Serializable {

    /** Creates a new instance of RulesBean */
    public RulesBean() {
    }

    // Actions -----------------------------------------------------------------------------------



    // Getters -----------------------------------------------------------------------------------
    
    public String getRulesText() {
        return "Ziadne pravidla";
    }

    // Setters -----------------------------------------------------------------------------------

    public void setContestId(String id) {
        
    }
}
