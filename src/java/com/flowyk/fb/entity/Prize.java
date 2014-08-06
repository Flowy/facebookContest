/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "prize")
@NamedQueries({
    @NamedQuery(name = "Prize.findAll", query = "SELECT p FROM Prize p"),
    @NamedQuery(name = "Prize.findByName", query = "SELECT p FROM Prize p WHERE p.name = :name"),
    @NamedQuery(name = "Prize.findByPosition", query = "SELECT p FROM Prize p WHERE p.prizePK.position = :position"),
    @NamedQuery(name = "Prize.findByContestId", query = "SELECT p FROM Prize p WHERE p.prizePK.contestId = :contestId")})
public class Prize implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @NotNull
    protected PrizePK prizePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "contest_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Contest contest;
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    @ManyToOne
    private RegisteredUser registeredUser;

    public Prize() {
    }

    public Prize(PrizePK prizePK) {
        this.prizePK = prizePK;
    }

    public Prize(PrizePK prizePK, String name) {
        this.prizePK = prizePK;
        this.name = name;
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

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prizePK != null ? prizePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Prize)) {
            return false;
        }
        Prize other = (Prize) object;
        return this.prizePK.equals(other.prizePK);
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.Prize[ prizePK=" + prizePK + " ]";
    }
    
}
