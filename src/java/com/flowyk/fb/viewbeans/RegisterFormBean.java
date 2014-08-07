/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.viewbeans;

import com.flowyk.fb.auth.FacebookLogin;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

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

    private RegisteredUser user;

    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+" //meno
            + "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*" //subdomena
            + "@(?:[a-zA-Z0-9]+\\.)+" //domena
            + "[a-zA-Z]{1,4}")  //root
    private String returningEmail;

    @AssertTrue
    private Boolean acceptedRules = false;

    /**
     * Creates a new instance of RegisterFormBean
     */
    public RegisterFormBean() {
    }

    @PostConstruct
    public void init() {
        user = new RegisteredUser();
    }

    // Actions -----------------------------------------------------------------------------------
    public String register() {
        user.setContest(contestBean.getActiveContest());
        user.setLocale(login.getSignedRequest().getLocale());
        user.setCountry(login.getSignedRequest().getCountry());
        user.setAgeMax(login.getSignedRequest().getAgeMax());
        user.setAgeMin(login.getSignedRequest().getAgeMin());
        try {
            utx.begin();
            Collection<RegisteredUser> userList = (Collection<RegisteredUser>) em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                    .setParameter("email", user.getEmail())
                    .setParameter("contest", user.getContest())
                    .getResultList();
            if (userList.isEmpty()) {
                em.persist(user);
                createNewTicket(user);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email už je zaregistrovaný", "Zadaný email už je zaregistrovaný"));
                utx.commit();
                contestBean.setReturning(Boolean.TRUE);
                return null;
            }
            utx.commit();
            return "registered";
        } catch (PersistenceException e) {
            Logger.getLogger(RegisterFormBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(RegisterFormBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * expects open transaction and persisted user
     */
    private void createNewTicket(RegisteredUser forUser) {
        Registration ticket = new Registration();
        ticket.setRegisteredUser(forUser);
        ticket.setTimeRegistered(new Date());
        ticket.setIpAddress(login.getSignedRequest().getIpAddress());
        ticket.setUserAgent(login.getSignedRequest().getUserAgent());
        em.persist(ticket);
    }

    public String registerNewTicket() {
        try {
            utx.begin();
            //if not exists returns exception
            RegisteredUser returningUser = (RegisteredUser) em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                    .setParameter("email", returningEmail)
                    .setParameter("contest", contestBean.getActiveContest())
                    .getSingleResult();
            //TODO: check if time after time interval for returning
            createNewTicket(returningUser);
            utx.commit();
            return "registered";
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email ešte nieje v súťaži", "Zadaný email ešte nieje v súťaži"));
            contestBean.setReturning(Boolean.FALSE);
            return null;
        } catch (PersistenceException ex) {
            Logger.getLogger(RegisterFormBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(RegisterFormBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
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

    public String getReturningEmail() {
        return returningEmail;
    }

    // Setters -----------------------------------------------------------------------------------
    public void setAcceptedRules(Boolean value) {
        acceptedRules = value;
    }

    public void setReturningEmail(String email) {
        this.returningEmail = email;
    }

}
