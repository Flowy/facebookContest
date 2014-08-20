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
import javax.persistence.FetchType;
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
    @NamedQuery(name = "Contest.findByContestStart", query = "SELECT c FROM Contest c WHERE c.contestStart = :contestStart"),
    @NamedQuery(name = "Contest.findByContestEnd", query = "SELECT c FROM Contest c WHERE c.contestEnd = :contestEnd"),
    @NamedQuery(name = "Contest.findByDisabled", query = "SELECT c FROM Contest c WHERE c.disabled = :disabled")})
public class Contest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_between_tickets", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date timeBetweenTickets;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "external_info_url", nullable = false, length = 250)
    private String externalInfoUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest", fetch = FetchType.EAGER)
    private List<RegisteredUser> registeredUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest", fetch = FetchType.EAGER)
    private List<Prize> prizeList;
    @Size(max = 150)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "registered_page_id", referencedColumnName = "page_id")
    @ManyToOne(optional = false)
    @NotNull
    private RegisteredPage registeredPage;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 150)
    @Column(name = "name")
    private String name;
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
    @Lob
    @Size(max = 65535)
    @Column(name = "rules")
    private String rules;
    @JoinColumn(name = "contest_layout_name", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private ContestLayout contestLayout;

    public Contest() {
    }

    public Contest(Date contestStart, Date contestEnd, boolean disabled) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public RegisteredPage getRegisteredPage() {
        return registeredPage;
    }

    public void setRegisteredPage(RegisteredPage registeredPage) {
        this.registeredPage = registeredPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalInfoUrl() {
        return externalInfoUrl;
    }

    public void setExternalInfoUrl(String externalInfoUrl) {
        this.externalInfoUrl = externalInfoUrl;
    }

    public List<RegisteredUser> getRegisteredUserList() {
        return registeredUserList;
    }

    public void setRegisteredUserList(List<RegisteredUser> registeredUserList) {
        this.registeredUserList = registeredUserList;
    }

    public List<Prize> getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(List<Prize> prizeList) {
        this.prizeList = prizeList;
    }

    public Date getTimeBetweenTickets() {
        return timeBetweenTickets;
    }

    public void setTimeBetweenTickets(Date timeBetweenTickets) {
        this.timeBetweenTickets = timeBetweenTickets;
    }
}
