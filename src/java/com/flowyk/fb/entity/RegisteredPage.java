/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "registered_page")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegisteredPage.findAll", query = "SELECT r FROM RegisteredPage r"),
    @NamedQuery(name = "RegisteredPage.findByPageId", query = "SELECT r FROM RegisteredPage r WHERE r.pageId = :pageId"),
    @NamedQuery(name = "RegisteredPage.findByActive", query = "SELECT r FROM RegisteredPage r WHERE r.active = :active"),
    @NamedQuery(name = "RegisteredPage.findByActiveUntil", query = "SELECT r FROM RegisteredPage r WHERE r.activeUntil = :activeUntil"),
    @NamedQuery(name = "RegisteredPage.findByActiveFrom", query = "SELECT r FROM RegisteredPage r WHERE r.activeFrom = :activeFrom"),
    @NamedQuery(name = "RegisteredPage.findByNote", query = "SELECT r FROM RegisteredPage r WHERE r.note = :note")})
public class RegisteredPage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "page_id", nullable = false, length = 20)
    private String pageId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active = false;
    @Column(name = "active_until")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeUntil;
    @Column(name = "active_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeFrom;
    @Size(max = 255)
    @Column(name = "note", length = 255)
    private String note;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registeredPage")
    private List<Contest> contestList;

    public RegisteredPage() {
    }

    public RegisteredPage(String pageId) {
        this.pageId = pageId;
    }

    public RegisteredPage(String pageId, boolean active) {
        this.pageId = pageId;
        this.active = active;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @XmlTransient
    public List<Contest> getContestList() {
        return contestList;
    }

    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageId != null ? pageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegisteredPage)) {
            return false;
        }
        RegisteredPage other = (RegisteredPage) object;
        if ((this.pageId == null && other.pageId != null) || (this.pageId != null && !this.pageId.equals(other.pageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.RegisteredPage[ pageId=" + pageId + " ]";
    }
    
}
