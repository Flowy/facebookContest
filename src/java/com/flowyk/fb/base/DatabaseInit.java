/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.base;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.ContestLayout;
import com.flowyk.fb.entity.RegisteredPage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Lukas
 */
@Singleton
//@Startup
public class DatabaseInit {
    
    private static final Logger LOG = Logger.getLogger(DatabaseInit.class.getName());

    @PersistenceContext(unitName = "fbContestDB")
    EntityManager em;

    @PostConstruct
    public void init() {

//        if (em.createNamedQuery("Contest.findByPage").setParameter("page", "663981640350558").getResultList().isEmpty()) {
        Contest c = new Contest();
        RegisteredPage p = new RegisteredPage();
        p.setPageId("663981640350558");
        p.setActive(true);
        c.setRegisteredPage(p);
        c.setContestStart(new Date(System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000));
        c.setContestEnd(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000));
        c.setDisabled(Boolean.FALSE);
        ContestLayout l = new ContestLayout("default");
        c.setContestLayout(l);
        try {
            em.persist(p);
            em.persist(l);
            em.persist(c);
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder("Constraints violated in DatabaseInit.init():\n");
            for (ConstraintViolation<?> constraint : e.getConstraintViolations()) {
                sb.append(constraint.getMessage()).append("\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Exception while database init", e);
        }
//        }
    }
}
