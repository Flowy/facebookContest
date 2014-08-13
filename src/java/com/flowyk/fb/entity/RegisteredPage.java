/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "registered_page")
@NamedQueries({
    @NamedQuery(name = "RegisteredPage.findAll", query = "SELECT r FROM RegisteredPage r"),
    @NamedQuery(name = "RegisteredPage.findByPageId", query = "SELECT r FROM RegisteredPage r WHERE r.pageId = :pageId"),
    @NamedQuery(name = "RegisteredPage.findByActive", query = "SELECT r FROM RegisteredPage r WHERE r.active = :active"),
    @NamedQuery(name = "RegisteredPage.findByActivationCode", query = "SELECT r FROM RegisteredPage r WHERE r.activationCode = :activationCode"),
    @NamedQuery(name = "RegisteredPage.findByActiveUntil", query = "SELECT r FROM RegisteredPage r WHERE r.activeUntil = :activeUntil"),
    @NamedQuery(name = "RegisteredPage.findByActiveFrom", query = "SELECT r FROM RegisteredPage r WHERE r.activeFrom = :activeFrom")})
public class RegisteredPage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "page_id")
    private String pageId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Size(max = 45)
    @Column(name = "activation_code")
    private String activationCode;
    @Column(name = "active_until")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeUntil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeFrom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registeredPage")
    private Collection<Contest> contestCollection;

    public RegisteredPage() {
    }

    public RegisteredPage(String pageId) {
        this.pageId = pageId;
    }

    public RegisteredPage(String pageId, boolean active, Date activeFrom) {
        this.pageId = pageId;
        this.active = active;
        this.activeFrom = activeFrom;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Date getActiveUntil() {
        return activeUntil;
    }

    public void setActiveUntil(Date activeUntil) {
        this.activeUntil = activeUntil;
    }

    public Date getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(Date activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Collection<Contest> getContestCollection() {
        return contestCollection;
    }

    public void setContestCollection(Collection<Contest> contestCollection) {
        System.out.println("RegisteredPage: setting new collection: " + contestCollection != null ? contestCollection.toString() : "");
        this.contestCollection = contestCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageId != null ? pageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RegisteredPage)) {
            return false;
        }
        RegisteredPage other = (RegisteredPage) object;
        return this.pageId.equals(other.pageId);
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.RegisteredPage[ pageId=" + pageId + " ]";
    }
    
}
