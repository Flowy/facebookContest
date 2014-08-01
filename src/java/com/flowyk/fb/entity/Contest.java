/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;


/**
 *
 * @author Lukas
 */
@Entity
@NamedQuery(name = "Contest.findByPage", query = "FROM Contest c WHERE c.registeredPage = :page")
public class Contest implements Serializable, Comparable<Contest> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String registeredPage;
    
    @OneToMany(mappedBy = "contest", cascade = {CascadeType.ALL})
    private List<RegisteredUser> registeredUsers;

    @OneToMany(mappedBy = "contest", cascade = {CascadeType.ALL})
    private List<Prize> prizes;
    
    private String name;
    private String description;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date contestStart;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date contestEnd;
    private String rulesUrl;
    private boolean disabled;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    @NotNull
    private ContestLayout layout = new ContestLayout();

    public ContestLayout getLayout() {
        return layout;
    }

    public void setLayout(ContestLayout layout) {
        this.layout = layout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getContestStart() {
        return contestStart;
    }

    public void setContestStart(Date contestStart) {
        this.contestStart = contestStart;
    }

    public Date getContestEnd() {
        return contestEnd;
    }

    public void setContestEnd(Date contestEnd) {
        this.contestEnd = contestEnd;
    }

    public String getRulesUrl() {
        return rulesUrl;
    }

    public void setRulesUrl(String rulesUrl) {
        this.rulesUrl = rulesUrl;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegisteredPage() {
        return registeredPage;
    }

    public void setRegisteredPage(String registeredPage) {
        this.registeredPage = registeredPage;
    }

    public List<RegisteredUser> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(List<RegisteredUser> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contest)) {
            return false;
        }
        Contest other = (Contest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.Contest[ id=" + id + " ]";
    }

    @Override
    public int compareTo(Contest o) {
        int compareEnd;
        int compareStart = 0;
        compareEnd = this.contestEnd.compareTo(o.contestEnd);
        if (compareEnd == 0) {
            compareStart = this.contestStart.compareTo(o.contestStart);
        }
        return compareEnd != 0 ? compareEnd : compareStart;
    }
    
}
