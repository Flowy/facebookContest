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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Contest.findByContestStart", query = "SELECT c FROM Contest c WHERE c.contestStart = :contestStart"),
    @NamedQuery(name = "Contest.findByContestEnd", query = "SELECT c FROM Contest c WHERE c.contestEnd = :contestEnd"),
    @NamedQuery(name = "Contest.findByDisabled", query = "SELECT c FROM Contest c WHERE c.disabled = :disabled"),
    @NamedQuery(name = "Contest.findByPopisSutaze", query = "SELECT c FROM Contest c WHERE c.popisSutaze = :popisSutaze"),
    @NamedQuery(name = "Contest.findByExterneInfoUrl", query = "SELECT c FROM Contest c WHERE c.externeInfoUrl = :externeInfoUrl")})
public class Contest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 20)
    private String registeredPage;
    @Size(max = 150)
    private String name;
    @Size(max = 250)
    private String iconUrl;
    @Lob
    @Size(max = 65535)
    private String description;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date contestStart;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date contestEnd;
    private Boolean disabled;
    @Size(max = 150)
    private String popisSutaze;
    @Size(max = 250)
    private String externeInfoUrl;
    @JoinColumn(name = "contestLayoutName", referencedColumnName = "name")
    @ManyToOne(optional = false, cascade={CascadeType.ALL})
    private Contestlayout contestLayoutName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contestId")
    private Collection<Registereduser> registereduserCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
    private Collection<Prize> prizeCollection;

    public Contest() {
    }

    public Contest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegisteredPage() {
        return registeredPage;
    }

    public void setRegisteredPage(String registeredPage) {
        this.registeredPage = registeredPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getPopisSutaze() {
        return popisSutaze;
    }

    public void setPopisSutaze(String popisSutaze) {
        this.popisSutaze = popisSutaze;
    }

    public String getExterneInfoUrl() {
        return externeInfoUrl;
    }

    public void setExterneInfoUrl(String externeInfoUrl) {
        this.externeInfoUrl = externeInfoUrl;
    }

    public Contestlayout getContestLayoutName() {
        return contestLayoutName;
    }

    public void setContestLayoutName(Contestlayout contestLayoutName) {
        this.contestLayoutName = contestLayoutName;
    }

    public Collection<Registereduser> getRegistereduserCollection() {
        return registereduserCollection;
    }

    public void setRegistereduserCollection(Collection<Registereduser> registereduserCollection) {
        this.registereduserCollection = registereduserCollection;
    }

    public Collection<Prize> getPrizeCollection() {
        return prizeCollection;
    }

    public void setPrizeCollection(Collection<Prize> prizeCollection) {
        this.prizeCollection = prizeCollection;
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
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.flowyk.entity.Contest[ id=" + id + " ]";
    }
    
}
