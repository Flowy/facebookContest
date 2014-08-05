/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
    @NamedQuery(name = "Prize.findByDescription", query = "SELECT p FROM Prize p WHERE p.description = :description"),
    @NamedQuery(name = "Prize.findByDetailUrl", query = "SELECT p FROM Prize p WHERE p.detailsUrl = :detailUrl"),
    @NamedQuery(name = "Prize.findByImageUrl", query = "SELECT p FROM Prize p WHERE p.imageUrl = :imageUrl"),
    @NamedQuery(name = "Prize.findByContestId", query = "SELECT p FROM Prize p WHERE p.prizePK.contestId = :contestId")})
public class Prize implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrizePK prizePK;
    @NotNull
    @Size(max=150)
    private String name;
    private String description;
    private String detailsUrl;
    private String imageUrl;
    
    @JoinColumn(name = "contestId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    private Contest contest;

    @JoinColumns({
        @JoinColumn(name = "winnerEmail", referencedColumnName = "email"),
        @JoinColumn(name = "winnerContestId", referencedColumnName = "contestId")})
    @ManyToOne
    private RegisteredUser winner;
    
    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PrizePK getPrizePK() {
        return prizePK;
    }

    public void setPrizePK(PrizePK prizePK) {
        this.prizePK = prizePK;
    }

    public RegisteredUser getWinner() {
        return winner;
    }

    public void setWinner(RegisteredUser winner) {
        this.winner = winner;
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
        return (this.prizePK != null || other.prizePK == null) && (this.prizePK == null || this.prizePK.equals(other.prizePK));
    }

    @Override
    public String toString() {
        return "com.flowyk.entity.Prize[ prizePK=" + prizePK + " ]";
    }
    
}
