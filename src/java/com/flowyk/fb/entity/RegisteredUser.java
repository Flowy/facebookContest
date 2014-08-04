/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
//@Table(
//        uniqueConstraints = {
//            @UniqueConstraint(name = "UniqueConstraint_contest_email", columnNames = {"contest", "email"})
//        }
//)
@IdClass(RegisteredUserKey.class)
public class RegisteredUser implements Serializable {

    @Id
    private String email;
    
    @Id
    @ManyToOne(cascade = {CascadeType.ALL})
    private Contest contest;
    
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
    
    @ManyToOne(cascade = {CascadeType.ALL})
    private RegisteredUser referal;
    @OneToMany(mappedBy = "referal", cascade = {CascadeType.ALL})
    private List<RegisteredUser> referies;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
    
    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
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
    
    @Override
    public String toString() {
        return "RegisteredUser[ name=" + name + ", contest=" + contest + ", email=" + email + " ]";
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

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

}
