/**
 * 
 */
package com.training.springdata.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author y.nadir
 *
 */
@Entity
@Table(name = "prs_ref_forme_juridique")
public class RefFormeJuridique implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    
    @Column(nullable = false)
    private String libelle;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CREATION", nullable = false)
    private Date dateCreation;
    
    protected RefFormeJuridique() {
        super();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
}
