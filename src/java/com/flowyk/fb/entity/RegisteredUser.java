/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Registereduser.findAll", query = "SELECT r FROM RegisteredUser r"),
    @NamedQuery(name = "Registereduser.findByEmail", query = "SELECT r FROM RegisteredUser r WHERE r.email = :email"),
    @NamedQuery(name = "Registereduser.findByName", query = "SELECT r FROM RegisteredUser r WHERE r.name = :name"),
    @NamedQuery(name = "Registereduser.findByTelephone", query = "SELECT r FROM RegisteredUser r WHERE r.telephone = :telephone"),
    @NamedQuery(name = "Registereduser.findByTickets", query = "SELECT r FROM RegisteredUser r WHERE r.tickets = :tickets"),
    @NamedQuery(name = "Registereduser.findByLocale", query = "SELECT r FROM RegisteredUser r WHERE r.locale = :locale"),
    @NamedQuery(name = "Registereduser.findByCountry", query = "SELECT r FROM RegisteredUser r WHERE r.country = :country"),
    @NamedQuery(name = "Registereduser.findByAgeMin", query = "SELECT r FROM RegisteredUser r WHERE r.ageMin = :ageMin"),
    @NamedQuery(name = "Registereduser.findByAgeMax", query = "SELECT r FROM RegisteredUser r WHERE r.ageMax = :ageMax"),
    @NamedQuery(name = "Registereduser.findById", query = "SELECT r FROM RegisteredUser r WHERE r.id = :id")})
public class RegisteredUser implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String email;
    @Size(max = 30)
    private String name;
    @Size(max = 16)
    private String telephone;
    private Integer tickets;
    @Size(max = 20)
    private String locale;
    @Size(max = 40)
    private String country;
    private Integer ageMin;
    private Integer ageMax;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @JoinColumn(name = "contestId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contest contestId;
    @OneToMany(mappedBy = "referalId")
    private Collection<RegisteredUser> registereduserCollection;
    @JoinColumn(name = "referalId", referencedColumnName = "id")
    @ManyToOne
    private RegisteredUser referalId;
    @OneToMany(mappedBy = "winnerId")
    private Collection<Prize> prizeCollection;

    public RegisteredUser() {
    }

    public RegisteredUser(Integer id) {
        this.id = id;
    }

    public RegisteredUser(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contest getContestId() {
        return contestId;
    }

    public void setContestId(Contest contestId) {
        this.contestId = contestId;
    }

    public Collection<RegisteredUser> getRegistereduserCollection() {
        return registereduserCollection;
    }

    public void setRegistereduserCollection(Collection<RegisteredUser> registereduserCollection) {
        this.registereduserCollection = registereduserCollection;
    }

    public RegisteredUser getReferalId() {
        return referalId;
    }

    public void setReferalId(RegisteredUser referalId) {
        this.referalId = referalId;
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
        if (!(object instanceof RegisteredUser)) {
            return false;
        }
        RegisteredUser other = (RegisteredUser) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.flowyk.entity.Registereduser[ id=" + id + " ]";
    }
    
}
