/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@Table(name = "registered_user", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "contest_id"})})
@XmlRootElement
@XmlType(propOrder = {"contest", "id", "email", "name", "telephone", "country", "ageMin", "ageMax", "removeFromContest"})
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "RegisteredUser.findAll", query = "SELECT r FROM RegisteredUser r"),
    @NamedQuery(name = "RegisteredUser.findById", query = "SELECT r FROM RegisteredUser r WHERE r.id = :id"),
    @NamedQuery(name = "RegisteredUser.findByContest", query = "SELECT r FROM RegisteredUser r WHERE r.contest = :contest"),
    @NamedQuery(name = "RegisteredUser.findByEmail", query = "SELECT r FROM RegisteredUser r WHERE r.email = :email"),
    @NamedQuery(name = "RegisteredUser.findByEmailAndContest", query = "SELECT r FROM RegisteredUser r WHERE r.contest = :contest AND r.email = :email"),
    @NamedQuery(name = "RegisteredUser.findByName", query = "SELECT r FROM RegisteredUser r WHERE r.name = :name"),
    @NamedQuery(name = "RegisteredUser.findByTelephone", query = "SELECT r FROM RegisteredUser r WHERE r.telephone = :telephone"),
    @NamedQuery(name = "RegisteredUser.findByLocale", query = "SELECT r FROM RegisteredUser r WHERE r.locale = :locale"),
    @NamedQuery(name = "RegisteredUser.findByCountry", query = "SELECT r FROM RegisteredUser r WHERE r.country = :country"),
    @NamedQuery(name = "RegisteredUser.findByAgeMin", query = "SELECT r FROM RegisteredUser r WHERE r.ageMin = :ageMin"),
    @NamedQuery(name = "RegisteredUser.findByAgeMax", query = "SELECT r FROM RegisteredUser r WHERE r.ageMax = :ageMax"),
    @NamedQuery(name = "RegisteredUser.findByRemoveFromContest", query = "SELECT r FROM RegisteredUser r WHERE r.removeFromContest = :removeFromContest"),
    @NamedQuery(name = "RegisteredUser.findByRegisteredPage", query = "SELECT r FROM RegisteredUser r WHERE r.contest.registeredPage = :registeredPage")
})
public class RegisteredUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue //(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+"  //meno
            + "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*"  //subdomena
            + "@(?:[a-zA-Z0-9]+\\.)+"  //domena
            + "[a-zA-Z]{1,4}")  //root
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Size(min = 5, max = 30)
    @Column(name = "name", length = 30)
    private String name;
    @Pattern(regexp = "[0-9 \\+]+")
    @Size(max = 16)
    @Column(name = "telephone", length = 16)
    private String telephone;
    @Size(max = 20)
    @Column(name = "locale", length = 20)
    @XmlTransient
    private String locale;
    @Size(max = 40)
    @Column(name = "country", length = 40)
    private String country;
    @Column(name = "age_min")
    private Integer ageMin;
    @Column(name = "age_max")
    private Integer ageMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "remove_from_contest", nullable = false)
    private boolean removeFromContest;
    @JoinColumn(name = "contest_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Contest contest;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registeredUser")
    @XmlTransient
    private List<Registration> registrationList;
    @OneToMany(mappedBy = "referal")
    @XmlTransient
    private List<Registration> referalToList;
    @OneToMany(mappedBy = "winner")
    @XmlTransient
    private List<Prize> prizeList;

    public RegisteredUser() {
    }

    public RegisteredUser(Integer id) {
        this.id = id;
    }

    public RegisteredUser(Integer id, String email, boolean removeFromContest) {
        this.id = id;
        this.email = email;
        this.removeFromContest = removeFromContest;
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

    public boolean getRemoveFromContest() {
        return removeFromContest;
    }

    public void setRemoveFromContest(boolean removeFromContest) {
        this.removeFromContest = removeFromContest;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    @XmlTransient
    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    @XmlTransient
    public List<Registration> getReferalList() {
        return referalToList;
    }

    public void setReferalList(List<Registration> referalList) {
        this.referalToList = referalList;
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
        if (!(object instanceof RegisteredUser)) {
            return false;
        }
        RegisteredUser other = (RegisteredUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "name: " + name + " email: " + email + " tel: " + telephone;
    }
    
}
