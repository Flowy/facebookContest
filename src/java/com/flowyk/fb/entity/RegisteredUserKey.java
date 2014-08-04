/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.entity;

import com.google.common.base.Objects;
import java.io.Serializable;

/**
 *
 * @author Lukas
 */

public class RegisteredUserKey implements Serializable {

    private Long contest;
    private String email;

    public Long getContest() {
        return contest;
    }

    public void setContest(Long contest) {
        this.contest = contest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash += Objects.hashCode(contest);
        hash += 31* Objects.hashCode(email);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegisteredUserKey)) {
            return false;
        }
        RegisteredUserKey other = (RegisteredUserKey) object;
        return contest.equals(other.contest) && email.equals(other.email);
    }
}
