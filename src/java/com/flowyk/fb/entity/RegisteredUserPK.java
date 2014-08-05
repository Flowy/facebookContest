/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Embeddable
public class RegisteredUserPK implements Serializable {
    @NotNull
    @Size(min = 5, max = 80)
    @Column(name = "email")
    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+"  //meno
            + "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*"  //subdomena
            + "@(?:[a-zA-Z0-9]+\\.)+"  //domena
            + "[a-zA-Z]{1,4}")  //root
    private String email;
    @NotNull
    @Column(name = "contestId")
    private Long contestId;

    public RegisteredUserPK() {
    }

    public RegisteredUserPK(String email, Long contestId) {
        this.email = email;
        this.contestId = contestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContestId() {
        return contestId;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        hash += contestId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegisteredUserPK)) {
            return false;
        }
        RegisteredUserPK other = (RegisteredUserPK) object;
        if (!this.email.equals(other.email)) {
            return false;
        }
        return this.contestId.equals(other.contestId);
    }

    @Override
    public String toString() {
        return "com.flowyk.entity.RegistereduserPK[ email=" + email + ", contestId=" + contestId + " ]";
    }
}
