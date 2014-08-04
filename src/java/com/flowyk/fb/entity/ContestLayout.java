/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Lukas
 */
@Entity
public class ContestLayout implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String name;

    public ContestLayout() { }
    public ContestLayout(String name) { this.name = name; }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the name fields are not set
        if (!(object instanceof ContestLayout)) {
            return false;
        }
        ContestLayout other = (ContestLayout) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.ContestLayout[ id=" + name + " ]";
    }
    
}
