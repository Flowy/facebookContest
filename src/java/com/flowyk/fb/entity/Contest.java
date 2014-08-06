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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "contest")
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
    @Basic(optional = false)
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "registered_page")
    private String registeredPage;
    @Size(max = 150)
    @Column(name = "name")
    private String name;
    @Size(max = 250)
    @Column(name = "icon_url")
    private String iconUrl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contest_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contestStart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contest_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contestEnd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disabled")
    private boolean disabled;
    @Size(max = 150)
    @Column(name = "popis_sutaze")
    private String popisSutaze;
    @Size(max = 250)
    @Column(name = "externe_info_url")
    private String externeInfoUrl;
    @Lob
    @Size(max = 65535)
    @Column(name = "rules")
    private String rules;
    @Column(name = "time_between_tickets")
    @Temporal(TemporalType.TIME)
    private Date timeBetweenTickets;
    @JoinColumn(name = "contest_layout_name", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private ContestLayout contestLayout;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
    private Collection<RegisteredUser> registeredUserCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
    private Collection<Prize> prizeCollection;

    public Contest() {
    }

    public Contest(Integer id) {
        this.id = id;
    }

    public Contest(Integer id, Date contestStart, Date contestEnd, boolean disabled) {
        this.id = id;
        this.contestStart = contestStart;
        this.contestEnd = contestEnd;
        this.disabled = disabled;
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

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
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

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public ContestLayout getContestLayout() {
        return contestLayout;
    }

    public void setContestLayout(ContestLayout contestLayout) {
        this.contestLayout = contestLayout;
    }

    public Collection<RegisteredUser> getRegisteredUserCollection() {
        return registeredUserCollection;
    }

    public void setRegisteredUserCollection(Collection<RegisteredUser> registeredUserCollection) {
        this.registeredUserCollection = registeredUserCollection;
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

    public Date getTimeBetweenTickets() {
        return timeBetweenTickets;
    }

    public void setTimeBetweenTickets(Date timeBetweenTickets) {
        this.timeBetweenTickets = timeBetweenTickets;
    }
    
}
