/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.entity.facade;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
@Stateless
public class ContestFacade extends AbstractFacade<Contest> {

    @PersistenceContext(unitName = "fbContestDB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContestFacade() {
        super(Contest.class);
    }

    public List<Contest> findByPage(RegisteredPage page) {
        List<Contest> list = em.createNamedQuery("Contest.findByRegisteredPage").setParameter("registeredPage", page).getResultList();
        return list;
    }
}
