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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "contest")
@XmlRootElement
@XmlType(propOrder = {"id"})
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Contest.findAll", query = "SELECT c FROM Contest c"),
    @NamedQuery(name = "Contest.findById", query = "SELECT c FROM Contest c WHERE c.id = :id"),
    @NamedQuery(name = "Contest.findByName", query = "SELECT c FROM Contest c WHERE c.name = :name"),
    @NamedQuery(name = "Contest.findByRegisteredPage", query = "SELECT c FROM Contest c WHERE c.registeredPage = :registeredPage"),
    @NamedQuery(name = "Contest.findByContestStart", query = "SELECT c FROM Contest c WHERE c.contestStart = :contestStart"),
    @NamedQuery(name = "Contest.findByContestEnd", query = "SELECT c FROM Contest c WHERE c.contestEnd = :contestEnd"),
    @NamedQuery(name = "Contest.findByDescription", query = "SELECT c FROM Contest c WHERE c.description = :description"),
    @NamedQuery(name = "Contest.findByExternalInfoUrl", query = "SELECT c FROM Contest c WHERE c.externalInfoUrl = :externalInfoUrl"),
    @NamedQuery(name = "Contest.findByTimeBetweenTickets", query = "SELECT c FROM Contest c WHERE c.timeBetweenTickets = :timeBetweenTickets")})
public class Contest implements Serializable, Comparable<Contest> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue //(strategy = GenerationType.IDENTITY)
    @NotNull
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "name", nullable = false, length = 150)
    @XmlTransient
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contest_start", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @XmlTransient
    private Date contestStart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contest_end", nullable = false)
    @XmlTransient
    @Temporal(TemporalType.TIMESTAMP)
    private Date contestEnd;
    @Size(max = 150)
    @Column(name = "description", length = 150)
    @XmlTransient
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "external_info_url", nullable = false, length = 250)
    @XmlTransient
    private String externalInfoUrl;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "rules", nullable = false, length = 65535)
    @XmlTransient
    private String rules;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_between_tickets", nullable = false)
    @Temporal(TemporalType.TIME)
    @XmlTransient
    private Date timeBetweenTickets = new Date((long) 1000 * 60 * 60 * 2);
    @JoinColumn(name = "contest_layout_name", referencedColumnName = "name", nullable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private ContestLayout contestLayout;
    @JoinColumn(name = "registered_page_id", referencedColumnName = "page_id", nullable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private RegisteredPage registeredPage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
    @XmlTransient
    private List<RegisteredUser> registeredUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
    @XmlTransient
    private List<Prize> prizeList;

    public Contest() {
    }

    public Contest(Integer id) {
        this.id = id;
    }

    public Contest(Integer id, String name, Date contestStart, Date contestEnd, String externalInfoUrl, String rules, Date timeBetweenTickets) {
        this.id = id;
        this.name = name;
        this.contestStart = contestStart;
        this.contestEnd = contestEnd;
        this.externalInfoUrl = externalInfoUrl;
        this.rules = rules;
        this.timeBetweenTickets = timeBetweenTickets;
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

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public Date getTimeBetweenTickets() {
        return timeBetweenTickets;
    }

    public void setTimeBetweenTickets(Date timeBetweenTickets) {
        this.timeBetweenTickets = timeBetweenTickets;
    }

    public ContestLayout getContestLayout() {
        return contestLayout;
    }

    public void setContestLayout(ContestLayout contestLayout) {
        this.contestLayout = contestLayout;
    }

    public RegisteredPage getRegisteredPage() {
        return registeredPage;
    }

    public void setRegisteredPage(RegisteredPage registeredPage) {
        this.registeredPage = registeredPage;
    }

    @XmlTransient
    public List<RegisteredUser> getRegisteredUserList() {
        return registeredUserList;
    }

    public void setRegisteredUserList(List<RegisteredUser> registeredUserList) {
        this.registeredUserList = registeredUserList;
    }

    @XmlTransient
    public List<Prize> getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(List<Prize> prizeList) {
        this.prizeList = prizeList;
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
