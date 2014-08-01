/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.viewbeans;

import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.oauth.FacebookLogin;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.AssertTrue;

/**
 *
 * @author Lukas
 */
@Named
@ViewScoped
public class RegisterFormBean implements Serializable {

    @PersistenceContext
    EntityManager em;
    
    @Inject
    FacebookLogin login;
    
    private RegisteredUser user = new RegisteredUser();
    @AssertTrue
    private Boolean acceptedRules = false;
    
    /** Creates a new instance of RegisterFormBean */
    public RegisterFormBean() {
    }

    // Actions -----------------------------------------------------------------------------------

    public void register() {
        user.setLocale(login.getSignedRequest().getLocale());
        user.setCountry(login.getSignedRequest().getCountry());
        user.setAgeMax(login.getSignedRequest().getAgeMax());
        user.setAgeMin(login.getSignedRequest().getAgeMin());
        
        System.out.println(user);
    }

    // Getters -----------------------------------------------------------------------------------
    
    public RegisteredUser getUser() {
        if (user == null) {
            user = new RegisteredUser();
        }
        return user;
    }
    
    public Boolean getAcceptedRules() {
        return acceptedRules;
    }

    // Setters -----------------------------------------------------------------------------------

    public void setAcceptedRules(Boolean value) {
        acceptedRules = value;
    }
}
