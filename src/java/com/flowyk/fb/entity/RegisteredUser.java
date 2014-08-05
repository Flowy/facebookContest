/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Registereduser.findAll", query = "SELECT r FROM RegisteredUser r"),
    @NamedQuery(name = "Registereduser.findByEmail", query = "SELECT r FROM RegisteredUser r WHERE r.userPK.email = :email"),
    @NamedQuery(name = "Registereduser.findByName", query = "SELECT r FROM RegisteredUser r WHERE r.name = :name"),
    @NamedQuery(name = "Registereduser.findByTelephone", query = "SELECT r FROM RegisteredUser r WHERE r.telephone = :telephone"),
    @NamedQuery(name = "Registereduser.findByTickets", query = "SELECT r FROM RegisteredUser r WHERE r.tickets = :tickets"),
    @NamedQuery(name = "Registereduser.findByLocale", query = "SELECT r FROM RegisteredUser r WHERE r.locale = :locale"),
    @NamedQuery(name = "Registereduser.findByCountry", query = "SELECT r FROM RegisteredUser r WHERE r.country = :country"),
    @NamedQuery(name = "Registereduser.findByAgeMin", query = "SELECT r FROM RegisteredUser r WHERE r.ageMin = :ageMin"),
    @NamedQuery(name = "Registereduser.findByAgeMax", query = "SELECT r FROM RegisteredUser r WHERE r.ageMax = :ageMax"),
    @NamedQuery(name = "Registereduser.findByContestId", query = "SELECT r FROM RegisteredUser r WHERE r.userPK.contestId = :contestId")})
public class RegisteredUser implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected RegisteredUserPK userPK;
    
    @JoinColumn(name = "contestId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    private Contest contest;
    
//    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+"  //meno
//            + "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*"  //subdomena
//            + "@(?:[a-zA-Z0-9]+\\.)+"  //domena
//            + "[a-zA-Z]{1,4}")  //root
//    private String email;
    
    @Size(min = 5, max = 30)
    private String name;
    @Pattern(regexp = "[0-9 \\+]+")
    @Size(min = 6, max = 16)
    private String telephone;
    private Integer tickets;
    @Size(max = 20)
    private String locale;
    @Size(max = 40)
    private String country;
    private Integer ageMin;
    private Integer ageMax;
    
    @JoinColumns({
        @JoinColumn(name = "referalEmail", referencedColumnName = "email"),
        @JoinColumn(name = "referalContestId", referencedColumnName = "contestId")})
    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    private RegisteredUser referal;
    @OneToMany(mappedBy = "referal", cascade = {CascadeType.ALL})
    private List<RegisteredUser> referies;
    
    public RegisteredUser() {
        
    }
    public RegisteredUser(RegisteredUserPK pk) {
        this.userPK = pk;
    }
    
    public Contest getContest() {
        return contest;
    }
//
//    public void setContest(Contest contest) {
//        this.contest = contest;
//    }
    
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

    public RegisteredUser getReferal() {
        return referal;
    }

    public void setReferal(RegisteredUser referal) {
        this.referal = referal;
    }

    public List<RegisteredUser> getReferies() {
        return referies;
    }

    public void setReferies(List<RegisteredUser> referies) {
        this.referies = referies;
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

    public RegisteredUserPK getUserPK() {
        return userPK;
    }

    public void setUserPK(RegisteredUserPK userPK) {
        this.userPK = userPK;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userPK != null ? userPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegisteredUser)) {
            return false;
        }
        RegisteredUser other = (RegisteredUser) object;
        boolean equals = (this.userPK != null || other.userPK == null) && (this.userPK == null || this.userPK.equals(other.userPK));
        return equals;
    }

    @Override
    public String toString() {
        return "com.flowyk.entity.Registereduser[ registereduserPK=" + userPK + " ]";
    }
}
