/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contestLayout", fetch = FetchType.EAGER)
    private List<Contest> contestList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;

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

    public List<Contest> getContestList() {
        return contestList;
    }

    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
    }
    
}
