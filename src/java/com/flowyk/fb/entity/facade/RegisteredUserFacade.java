/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.entity.facade;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
@Stateless
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

    public RegisteredUser findByContestAndEmail(Contest contest, String email) {
        try {
            RegisteredUser user = (RegisteredUser) em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                    .setParameter("email", email)
                    .setParameter("contest", contest)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }
}
