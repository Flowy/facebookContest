/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.base;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.ContestLayout;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukas
 */
@Singleton
@Startup
public class DatabaseInit {

    @PersistenceContext(unitName = "fbContestDB")
    EntityManager em;

    @PostConstruct
    public void init() {

//        if (em.createNamedQuery("Contest.findByPage").setParameter("page", "663981640350558").getResultList().isEmpty()) {
            Contest c = new Contest();
            c.setRegisteredPage("663981640350558");
            c.setContestStart(new Date(System.currentTimeMillis() - 10*24*60*60*1000));
            c.setContestEnd(new Date(System.currentTimeMillis() + 10*24*60*60*1000));
            ContestLayout l = new ContestLayout("default");
            c.setContestLayoutName(l);
            em.persist(c);
//        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
