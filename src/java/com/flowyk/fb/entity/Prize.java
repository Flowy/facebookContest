/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lukas
 */
@Entity
@Table(name = "prize")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prize.findAll", query = "SELECT p FROM Prize p"),
    @NamedQuery(name = "Prize.findByName", query = "SELECT p FROM Prize p WHERE p.name = :name"),
    @NamedQuery(name = "Prize.findById", query = "SELECT p FROM Prize p WHERE p.id = :id")})
public class Prize implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = true)
    @Size(min = 1, max = 250)
    @Column(name = "name", nullable = false, length = 250)
    private String name;
    @Id
    @GeneratedValue //(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "contest_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Contest contest;
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    @ManyToOne
    private RegisteredUser winner;

    public Prize() {
    }

    public Prize(Integer id) {
        this.id = id;
    }

    public Prize(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prize)) {
            return false;
        }
        Prize other = (Prize) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flowyk.fb.entity.Prize[ id=" + id + " ]";
    }
    
}
