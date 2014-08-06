/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.viewbeans;

import com.flowyk.fb.entity.Registereduser;
import com.flowyk.fb.auth.FacebookLogin;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
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

    @Resource
    private UserTransaction utx;

    @Inject
    FacebookLogin login;

    @Inject
    ContestBean contestBean;

    private Registereduser user;
    @AssertTrue
    private Boolean acceptedRules = false;

    /**
     * Creates a new instance of RegisterFormBean
     */
    public RegisterFormBean() {
    }

    @PostConstruct
    public void init() {
        user = new Registereduser();
        user.setContestId(contestBean.getActiveContest());
        user.setLocale(login.getSignedRequest().getLocale());
        user.setCountry(login.getSignedRequest().getCountry());
        user.setAgeMax(login.getSignedRequest().getAgeMax());
        user.setAgeMin(login.getSignedRequest().getAgeMin());
    }

    // Actions -----------------------------------------------------------------------------------

    public String register() {
        try {
            utx.begin();
            em.persist(user);
            utx.commit();
        } catch (PersistenceException e) {
            Logger.getLogger(RegisterFormBean.class.getName()).log(Level.SEVERE, null, e);
            return "registered";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(RegisterFormBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "registered";
    }

    // Getters -----------------------------------------------------------------------------------
    public Registereduser getUser() {
        if (user == null) {
            user = new Registereduser();
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
