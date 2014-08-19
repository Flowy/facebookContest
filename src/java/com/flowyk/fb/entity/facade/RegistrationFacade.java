/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity.facade;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
@Stateless
public class RegistrationFacade extends AbstractFacade<Registration> {
    @PersistenceContext(unitName = "fbContestDB")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistrationFacade() {
        super(Registration.class);
    }
    
    public int getTicketWeightForUser(RegisteredUser user) {
        int weight = 0;
        List<Registration> list = em.createNamedQuery("Registration.findByRegisteredUser").setParameter("registeredUser", user).getResultList();
        for (Registration reg : list) {
            weight += reg.getWeight();
        }
        return weight;
    }

    /**
     *
     * @param user
     * @return registered time of last ticket
     */
    public Date getLastTicketTime(RegisteredUser user) {
        List<Registration> list = em.createNamedQuery("Registration.findByRegisteredUser").setParameter("registeredUser", user).getResultList();
        Date last = new Date(Long.MIN_VALUE);
        if (Constants.FINE_DEBUG) {
            Logger.getLogger(RegistrationFacade.class.getName()).log(Level.INFO, "Found registrations: {0}\r\n for user: {1}", new Object[]{list != null ? list.toString() : "none", user});
        }
        for (Registration reg : list) {
            if (reg.getTimeRegistered().after(last)) {
                last = reg.getTimeRegistered();
            }
        }
        return last;
    }
    
}
