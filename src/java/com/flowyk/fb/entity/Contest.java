/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Contest.findAll", query = "SELECT c FROM Contest c"),
    @NamedQuery(name = "Contest.findById", query = "SELECT c FROM Contest c WHERE c.id = :id"),
    @NamedQuery(name = "Contest.findByRegisteredPage", query = "SELECT c FROM Contest c WHERE c.registeredPage = :registeredPage"),
    @NamedQuery(name = "Contest.findByName", query = "SELECT c FROM Contest c WHERE c.name = :name"),
    @NamedQuery(name = "Contest.findByIconUrl", query = "SELECT c FROM Contest c WHERE c.iconUrl = :iconUrl"),
    @NamedQuery(name = "Contest.findByDescription", query = "SELECT c FROM Contest c WHERE c.description = :description"),
    @NamedQuery(name = "Contest.findByContestStart", query = "SELECT c FROM Contest c WHERE c.contestStart = :contestStart"),
    @NamedQuery(name = "Contest.findByContestEnd", query = "SELECT c FROM Contest c WHERE c.contestEnd = :contestEnd"),
    @NamedQuery(name = "Contest.findByDisabled", query = "SELECT c FROM Contest c WHERE c.disabled = :disabled"),
    @NamedQuery(name = "Contest.findByPage", query = "FROM Contest c WHERE c.registeredPage = :page")})
public class Contest implements Serializable, Comparable<Contest> {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max=20)
    private String registeredPage;

    @OneToMany(mappedBy = "contest", cascade = {CascadeType.ALL})
    private Collection<RegisteredUser> registeredUsers;

    @OneToMany(mappedBy = "contest", cascade = {CascadeType.ALL})
    private Collection<Prize> prizes;

    @Size(max=150)
    private String name;
    @Size(max=150)
    private String iconUrl;
    private String description;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date contestStart;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date contestEnd;
    private String rules;
    private boolean disabled;

    @NotNull
    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    private ContestLayout layout;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

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

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Collection<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(Collection<Prize> prizes) {
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

    public Collection<RegisteredUser> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(Collection<RegisteredUser> registeredUsers) {
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
        if (!(object instanceof Contest)) {
            return false;
        }
        Contest other = (Contest) object;
        return this.id.equals(other.id);
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
