/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity.facade;

import com.flowyk.fb.entity.RegisteredUser;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
public class RegisteredUserFacade extends AbstractFacade<RegisteredUser> {
    @PersistenceContext(unitName = "fbContestDB")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegisteredUserFacade() {
        super(RegisteredUser.class);
    }
    
}
