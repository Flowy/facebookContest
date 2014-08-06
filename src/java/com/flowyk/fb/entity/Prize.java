/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Prize.findAll", query = "SELECT p FROM Prize p"),
    @NamedQuery(name = "Prize.findByName", query = "SELECT p FROM Prize p WHERE p.name = :name"),
    @NamedQuery(name = "Prize.findByPosition", query = "SELECT p FROM Prize p WHERE p.prizePK.position = :position"),
    @NamedQuery(name = "Prize.findByContestId", query = "SELECT p FROM Prize p WHERE p.prizePK.contestId = :contestId")})
public class Prize implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrizePK prizePK;
    @Size(max = 250)
    @NotNull
    private String name;
    @JoinColumn(name = "contestId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Contest contest;
    @JoinColumn(name = "winnerId", referencedColumnName = "id")
    @ManyToOne
    private RegisteredUser winnerId;

    public Prize() {
    }

    public Prize(PrizePK prizePK) {
        this.prizePK = prizePK;
    }

    public Prize(int position, int contestId) {
        this.prizePK = new PrizePK(position, contestId);
    }

    public PrizePK getPrizePK() {
        return prizePK;
    }

    public void setPrizePK(PrizePK prizePK) {
        this.prizePK = prizePK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public RegisteredUser getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(RegisteredUser winnerId) {
        this.winnerId = winnerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prizePK != null ? prizePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prize)) {
            return false;
        }
        Prize other = (Prize) object;
        if ((this.prizePK == null && other.prizePK != null) || (this.prizePK != null && !this.prizePK.equals(other.prizePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flowyk.entity.Prize[ prizePK=" + prizePK + " ]";
    }
    
}
