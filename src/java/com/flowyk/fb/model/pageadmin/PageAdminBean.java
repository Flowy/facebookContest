/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.pageadmin;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.model.Login;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 * must have login initialized
 * @author Lukas
 */
@Named
@RequestScoped
public class PageAdminBean implements Serializable {

    @Inject
    private Login login;

    @EJB
    RegisteredPageFacade registeredPageFacade;
    
    @EJB
    ContestFacade contestFacade;
    
    @Inject
    ContestEditBean contestEditBean;

    // Actions -----------------------------------------------------------------------------------
    
    public String activate() {
        RegisteredPage page = login.getPage();
        page.setActive(true);
        registeredPageFacade.edit(page);
        return "admin";
    }
    
    public String createNewContest() {
        contestEditBean.createNewContest();
        return "contest-edit";
    }

    // Getters -----------------------------------------------------------------------------------
    public List<Contest> getContests() {
        List<Contest> list = contestFacade.findByPage(login.getPage());
        return list;
    }

    // Setters -----------------------------------------------------------------------------------
}
