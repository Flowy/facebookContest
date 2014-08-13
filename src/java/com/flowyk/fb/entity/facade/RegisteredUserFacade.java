/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.entity.facade;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredUser;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
@Stateless
public class RegisteredUserFacade extends AbstractFacade<RegisteredUser> {

    @PersistenceContext(unitName = "fbContestDB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegisteredUserFacade() {
        super(RegisteredUser.class);
    }

    public List<RegisteredUser> findByContestAndEmail(Contest contest, String email) {
        List<RegisteredUser> userList = em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                .setParameter("email", email)
                .setParameter("contest", contest)
                .getResultList();
        
        return userList;
    }

}
