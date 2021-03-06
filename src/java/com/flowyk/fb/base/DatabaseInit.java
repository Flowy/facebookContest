/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.base;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.ContestLayout;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.ContestLayoutFacade;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Lukas
 */
@Singleton
@Startup
public class DatabaseInit {

    private static final Logger LOG = Logger.getLogger(DatabaseInit.class.getName());

    @EJB
    RegisteredPageFacade registeredPageFacade;

    @EJB
    ContestLayoutFacade contestLayoutFacade;

    @EJB
    ContestFacade contestFacade;

    @PostConstruct
    public void init() {
        if (contestLayoutFacade.find("default") == null) {
            ContestLayout layout = new ContestLayout("default");
            contestLayoutFacade.create(layout);
        }
//        if (registeredPageFacade.count() == 0) {
//            RegisteredPage p = new RegisteredPage();
//            p.setPageId("663981640350558");
//            p.setActiveFrom(new Date(System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000));
//            p.setActive(true);
//            registeredPageFacade.create(p);
//            ContestLayout l = new ContestLayout("default");
//            contestLayoutFacade.create(l);
//            Contest c = new Contest();
//            c.setRegisteredPage(p);
//            c.setContestStart(new Date(System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000));
//            c.setContestEnd(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000));
//            c.setDisabled(Boolean.FALSE);
//            java.sql.Time ticketsDelay = new java.sql.Time(1000*60*60*2);
//            c.setTimeBetweenTickets(ticketsDelay);
//            c.setDescription("Popis sutaze");
//            c.setExternalInfoUrl("http://stackoverflow.com/questions/15359306/how-to-load-lazy-fetched-items-from-hibernate-jpa-in-my-controller");
//            c.setName("Constest name");
//            c.setRules("Contest rules are pretty long in real");
//            c.setContestLayout(l);
//            contestFacade.create(c);
//        }
    }
}
