/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@Embeddable
public class PrizePK implements Serializable {
    @NotNull
    private int position;
    @NotNull
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "com.flowyk.entity.PrizePK[ position=" + position + ", contestId=" + contestId + " ]";
    }
    
}
