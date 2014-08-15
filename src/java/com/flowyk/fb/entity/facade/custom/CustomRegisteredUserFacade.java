/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity.facade.custom;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Lukas
 */
@Stateless
public class CustomRegisteredUserFacade extends RegisteredUserFacade {

    public CustomRegisteredUserFacade() {
        super();
    }

    public List<RegisteredUser> findByContestAndEmail(Contest contest, String email) {
        List<RegisteredUser> userList = em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                .setParameter("email", email)
                .setParameter("contest", contest)
                .getResultList();
        
        return userList;
    }
    
}
