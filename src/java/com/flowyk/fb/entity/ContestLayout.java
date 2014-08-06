/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "contest_layout")
@NamedQueries({
    @NamedQuery(name = "ContestLayout.findAll", query = "SELECT c FROM ContestLayout c"),
    @NamedQuery(name = "ContestLayout.findByName", query = "SELECT c FROM ContestLayout c WHERE c.name = :name")})
public class ContestLayout implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contestLayout")
    private Collection<Contest> contestCollection;

    public ContestLayout() {
    }

    public ContestLayout(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Contest> getContestCollection() {
        return contestCollection;
    }

    public void setContestCollection(Collection<Contest> contestCollection) {
        this.contestCollection = contestCollection;
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
        return "com.flowyk.fb.entity.ContestLayout[ name=" + name + " ]";
    }
    
}
