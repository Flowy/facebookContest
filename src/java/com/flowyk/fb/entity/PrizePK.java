/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@Embeddable
public class PrizePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "position")
    private int position;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contest_id")
    private int contestId;

    public PrizePK() {
    }

    public PrizePK(int position, int contestId) {
        this.position = position;
        this.contestId = contestId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) position;
        hash += (int) contestId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PrizePK)) {
            return false;
        }
        PrizePK other = (PrizePK) object;
        if (this.position != other.position) {
            return false;
        }
        return this.contestId == other.contestId;
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.PrizePK[ position=" + position + ", contestId=" + contestId + " ]";
    }
    
}
