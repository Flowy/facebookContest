/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model;

import com.flowyk.fb.entity.RegisteredUser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Lukas
 */
@Named(value = "returningBean")
@ViewScoped
public class ReturningBean {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    ContestBean contestBean;
    
    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+" //meno
            + "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*" //subdomena
            + "@(?:[a-zA-Z0-9]+\\.)+" //domena
            + "[a-zA-Z]{1,4}")  //root
    private String returningEmail;
    
    public String registerNewTicket() {
        try {
//            utx.begin();
            //if not exists returns exception
            RegisteredUser returningUser = (RegisteredUser) em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                    .setParameter("email", returningEmail)
                    .setParameter("contest", contestBean.getActiveContest())
                    .getSingleResult();
            //TODO: check if time after time interval for returning
            contestBean.createNewTicket(returningUser);
//            utx.commit();
            return "thanks";
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email ešte nieje v súťaži", "Zadaný email ešte nieje v súťaži"));
            contestBean.setReturning(Boolean.FALSE);
            return null;
        } catch (PersistenceException ex) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
//        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
//            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
    }

    public String getReturningEmail() {
        return returningEmail;
    }

    public void setReturningEmail(String returningEmail) {
        this.returningEmail = returningEmail;
    }
    
}
