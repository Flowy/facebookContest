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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "registered_user", uniqueConstraints = {
    @UniqueConstraint(name = "email_contestId_unique", columnNames = { "email", "contest_id" } )
})
@NamedQueries({
    @NamedQuery(name = "RegisteredUser.findAll", query = "SELECT r FROM RegisteredUser r"),
    @NamedQuery(name = "RegisteredUser.findById", query = "SELECT r FROM RegisteredUser r WHERE r.id = :id"),
    @NamedQuery(name = "RegisteredUser.findByContest", query = "SELECT r FROM RegisteredUser r WHERE r.contest = :contest"),
    @NamedQuery(name = "RegisteredUser.findByEmailAndContest", query = "SELECT r FROM RegisteredUser r WHERE r.contest = :contest AND r.email = :email"),
    @NamedQuery(name = "RegisteredUser.findByTelephone", query = "SELECT r FROM RegisteredUser r WHERE r.telephone = :telephone"),
    @NamedQuery(name = "RegisteredUser.findByLocale", query = "SELECT r FROM RegisteredUser r WHERE r.locale = :locale"),
    @NamedQuery(name = "RegisteredUser.findByCountry", query = "SELECT r FROM RegisteredUser r WHERE r.country = :country"),
    @NamedQuery(name = "RegisteredUser.findByAgeMin", query = "SELECT r FROM RegisteredUser r WHERE r.ageMin = :ageMin"),
    @NamedQuery(name = "RegisteredUser.findByAgeMax", query = "SELECT r FROM RegisteredUser r WHERE r.ageMax = :ageMax")})
public class RegisteredUser implements Serializable {
    @Column(name = "tickets")
    private Integer tickets;
    @Column(name = "age_min")
    private Integer ageMin;
    @Column(name = "age_max")
    private Integer ageMax;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registeredUser")
    private Collection<Registration> registrationCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+"  //meno
            + "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*"  //subdomena
            + "@(?:[a-zA-Z0-9]+\\.)+"  //domena
            + "[a-zA-Z]{1,4}")  //root
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Size(min = 5, max = 30)
    @Column(name = "name")
    private String name;
    @Pattern(regexp = "[0-9 \\+]+")
    @Size(max = 16)
    @Column(name = "telephone")
    private String telephone;
    @Size(max = 20)
    @Column(name = "locale")
    private String locale;
    @Size(max = 40)
    @Column(name = "country")
    private String country;
    @JoinColumn(name = "contest_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contest contest;
    @OneToMany(mappedBy = "registeredUser")
    private Collection<RegisteredUser> registeredUserCollection;
    @JoinColumn(name = "referal_id", referencedColumnName = "id")
    @ManyToOne
    private RegisteredUser registeredUser;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "registeredUser")
    private Registration registration;
    @OneToMany(mappedBy = "registeredUser")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Collection<RegisteredUser> getRegisteredUserCollection() {
        return registeredUserCollection;
    }

    public void setRegisteredUserCollection(Collection<RegisteredUser> registeredUserCollection) {
        this.registeredUserCollection = registeredUserCollection;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
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
        if (!(object instanceof RegisteredUser)) {
            return false;
        }
        RegisteredUser other = (RegisteredUser) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.RegisteredUser[ id=" + id + " ]";
    }

    public Collection<Registration> getRegistrationCollection() {
        return registrationCollection;
    }

    public void setRegistrationCollection(Collection<Registration> registrationCollection) {
        this.registrationCollection = registrationCollection;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }
    
}
