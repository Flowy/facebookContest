/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Contestlayout.findAll", query = "SELECT c FROM ContestLayout c"),
    @NamedQuery(name = "Contestlayout.findByName", query = "SELECT c FROM ContestLayout c WHERE c.name = :name")})
public class ContestLayout implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Size(min=4, max=20)
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
        if (!(object instanceof ContestLayout)) {
            return false;
        }
        ContestLayout other = (ContestLayout) object;
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.ContestLayout[ id=" + name + " ]";
    }
    
}
