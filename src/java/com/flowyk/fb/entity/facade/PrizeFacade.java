/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity.facade;

import com.flowyk.fb.entity.Prize;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
@Stateless
public class PrizeFacade extends AbstractFacade<Prize> {
    @PersistenceContext(unitName = "fbContestDB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrizeFacade() {
        super(Prize.class);
    }
    
}
