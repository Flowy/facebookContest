/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity.facade.custom;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.facade.ContestFacade;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Lukas
 */
@Stateless
public class CustomContestFacade extends ContestFacade {

    public CustomContestFacade() {
        super();
    }
    
    public List<Contest> findByPage(RegisteredPage page) {
        List<Contest> list = em.createNamedQuery("Contest.findByRegisteredPage").setParameter("registeredPage", page).getResultList();
        return list;
    }
    
}
