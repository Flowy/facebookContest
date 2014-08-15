/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity.facade;

import com.flowyk.fb.entity.Registration;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
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
    
}
