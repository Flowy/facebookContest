/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.jaxb;

import com.flowyk.fb.entity.RegisteredUser;
import java.util.List;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Lukas
 */
@XmlRootElement(name="contest")
@XmlSeeAlso({RegisteredUser.class})
public class ContestUsers {
    private List<RegisteredUser> users;
    private String contestName;
    private Integer contestId;
    
    @XmlElementWrapper(name="users")
    @XmlElementRef()
    public List<RegisteredUser> getUsers() {
        return users;
    }
    
    public ContestUsers(List<RegisteredUser> users) {
        this.users = users;
    }
    
    public ContestUsers() {
        
    }
}
