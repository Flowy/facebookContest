/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity.facade.custom;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.entity.facade.RegistrationFacade;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Lukas
 */
@Stateless
public class CustomRegistrationFacade extends RegistrationFacade {

    public CustomRegistrationFacade() {
        super();
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
    public Calendar getLastTicketTime(RegisteredUser user) {
        List<Registration> list = em.createNamedQuery("Registration.findByRegisteredUser").setParameter("registeredUser", user).getResultList();
        Calendar last = Calendar.getInstance();
        last.setTimeInMillis(0L);
        if (Constants.FINE_DEBUG) {
            Logger.getLogger(CustomRegistrationFacade.class.getName()).log(Level.INFO, "Found registrations: {0}\r\n for user: {1}", new Object[]{list != null ? list.toString() : "none", user});
        }
        for (Registration reg : list) {
            if (reg.getTimeRegistered().after(last)) {
                last = reg.getTimeRegistered();
            }
        }
        return last;
    }
    
}
