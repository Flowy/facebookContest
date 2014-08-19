/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "registration")
@NamedQueries({
    @NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r"),
    @NamedQuery(name = "Registration.findById", query = "SELECT r FROM Registration r WHERE r.id = :id"),
    @NamedQuery(name = "Registration.findByTimeRegistered", query = "SELECT r FROM Registration r WHERE r.timeRegistered = :timeRegistered"),
    @NamedQuery(name = "Registration.findByIpAddress", query = "SELECT r FROM Registration r WHERE r.ipAddress = :ipAddress"),
    @NamedQuery(name = "Registration.findByUserAgent", query = "SELECT r FROM Registration r WHERE r.userAgent = :userAgent"), 
    @NamedQuery(name = "Registration.findByRegisteredUser", query = "SELECT r FROM Registration r WHERE r.registeredUser = :registeredUser")
})
public class Registration implements Serializable, Comparable<Registration> {
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_registered", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeRegistered;
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "weight")
    private int weight;
    @NotNull
    @JoinColumn(name = "registered_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RegisteredUser registeredUser;
    @JoinColumn(name = "referal_id", referencedColumnName = "id")
    @ManyToOne
    private RegisteredUser referal;
    @Id
    @NotNull
    @GeneratedValue
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "ip_address")
    private String ipAddress;
    @Size(max = 400)
    @Column(name = "user_agent")
    private String userAgent;

    public Registration() {
    }

    public Registration(Integer id) {
        this.id = id;
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        return this.id.equals(other.id);
    }

    
    @Override
    public String toString() {
        return "com.flowyk.fb.entity.Registration[ id=" + id + " ]";
    }

    @Override
    public int compareTo(Registration o) {
        return this.timeRegistered.compareTo(o.timeRegistered);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public RegisteredUser getReferal() {
        return referal;
    }

    public void setReferal(RegisteredUser referal) {
        this.referal = referal;
    }

    public Date getTimeRegistered() {
        return timeRegistered;
    }

    public void setTimeRegistered(Date timeRegistered) {
        this.timeRegistered = timeRegistered;
    }
    
}
