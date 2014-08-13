/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity.facade;

import com.flowyk.fb.entity.ContestLayout;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
@Stateless
public class ContestLayoutFacade extends AbstractFacade<ContestLayout> {
    @PersistenceContext(unitName = "fbContestDB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContestLayoutFacade() {
        super(ContestLayout.class);
    }
    
}
