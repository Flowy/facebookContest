/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model;

import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@Named(value = "registrationBean")
@ViewScoped
public class RegistrationBean {

    
    @PersistenceContext
    private EntityManager em;
    
    @NotNull
    private final RegisteredUser activeUser;
    
    @AssertTrue
    private Boolean acceptedRules = false;
    
    @Inject
    ContestBean contestBean;
    
    @Inject
    SignedRequest signedRequest;
    
    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
        activeUser = new RegisteredUser();
    }
    
    
    public String register() {
        activeUser.setContest(contestBean.getActiveContest());
        activeUser.setLocale(signedRequest.getUser().getLocale());
        activeUser.setCountry(signedRequest.getUser().getCountry());
        activeUser.setAgeMax(signedRequest.getUser().getAgeMax());
        activeUser.setAgeMin(signedRequest.getUser().getAgeMin());
        try {
//            utx.begin();
            Collection<RegisteredUser> userList = (Collection<RegisteredUser>) em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                    .setParameter("email", activeUser.getEmail())
                    .setParameter("contest", activeUser.getContest())
                    .getResultList();
            if (userList.isEmpty()) {
                em.persist(activeUser);
                contestBean.createNewTicket(activeUser);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email už je zaregistrovaný", "Zadaný email už je zaregistrovaný"));
//                utx.commit();
                contestBean.setReturning(Boolean.TRUE);
                return null;
            }
//            utx.commit();
            contestBean.setReturning(Boolean.TRUE);
            return "thanks";
        } catch (PersistenceException e) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
//        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
//            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
    }

    public RegisteredUser getActiveUser() {
        return activeUser;
    }

    public Boolean isAcceptedRules() {
        return acceptedRules;
    }

    public void setAcceptedRules(Boolean acceptedRules) {
        this.acceptedRules = acceptedRules;
    }

    
}
