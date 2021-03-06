/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "registration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r"),
    @NamedQuery(name = "Registration.findById", query = "SELECT r FROM Registration r WHERE r.id = :id"),
    @NamedQuery(name = "Registration.findByIpAddress", query = "SELECT r FROM Registration r WHERE r.ipAddress = :ipAddress"),
    @NamedQuery(name = "Registration.findByTimeRegistered", query = "SELECT r FROM Registration r WHERE r.timeRegistered = :timeRegistered"),
    @NamedQuery(name = "Registration.findByUserAgent", query = "SELECT r FROM Registration r WHERE r.userAgent = :userAgent"),
    @NamedQuery(name = "Registration.findByWeight", query = "SELECT r FROM Registration r WHERE r.weight = :weight"),
    @NamedQuery(name = "Registration.findActiveByContest",
            query = "SELECT r FROM Registration r WHERE r.registeredUser.removeFromContest = false AND r.registeredUser.contest = :contest")
})
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue //(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 50)
    @Column(name = "ip_address", length = 50)
    private String ipAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_registered", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeRegistered;
    @Size(max = 255)
    @Column(name = "user_agent", length = 255)
    private String userAgent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weight", nullable = false)
    private int weight;
    @JoinColumn(name = "registered_user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private RegisteredUser registeredUser;
    @JoinColumn(name = "referal_id", referencedColumnName = "id")
    @ManyToOne
    private RegisteredUser referal;

    public Registration() {
    }

    public Registration(Integer id) {
        this.id = id;
    }

    public Registration(Integer id, Date timeRegistered, int weight) {
        this.id = id;
        this.timeRegistered = timeRegistered;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getTimeRegistered() {
        return timeRegistered;
    }

    public void setTimeRegistered(Date timeRegistered) {
        this.timeRegistered = timeRegistered;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public RegisteredUser getReferal() {
        return referal;
    }

    public void setReferal(RegisteredUser referal) {
        this.referal = referal;
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
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.Registration[ id=" + id + " ]";
    }

}
