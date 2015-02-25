/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.persistent.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mussa
 */
@Entity
@Table(name = "medications", catalog = "MRRS", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medications.findAll", query = "SELECT m FROM Medications m"),
    @NamedQuery(name = "Medications.findById", query = "SELECT m FROM Medications m WHERE m.id = :id"),
    @NamedQuery(name = "Medications.findByName", query = "SELECT m FROM Medications m WHERE m.name = :name"),
    @NamedQuery(name = "Medications.findByRecipe", query = "SELECT m FROM Medications m WHERE m.recipe = :recipe"),
    @NamedQuery(name = "Medications.findByRecipeDATE", query = "SELECT m FROM Medications m WHERE m.recipeDATE = :recipeDATE")})
public class Medications implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "recipe", nullable = false, length = 100)
    private String recipe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "recipeDATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date recipeDATE;
    @JoinColumn(name = "RECORD_ID", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private MedicalRecord recordId;

    public Medications() {
    }

    public Medications(Integer id) {
        this.id = id;
    }

    public Medications(Integer id, String name, String recipe, Date recipeDATE) {
        this.id = id;
        this.name = name;
        this.recipe = recipe;
        this.recipeDATE = recipeDATE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Date getRecipeDATE() {
        return recipeDATE;
    }

    public void setRecipeDATE(Date recipeDATE) {
        this.recipeDATE = recipeDATE;
    }

    public MedicalRecord getRecordId() {
        return recordId;
    }

    public void setRecordId(MedicalRecord recordId) {
        this.recordId = recordId;
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
        if (!(object instanceof Medications)) {
            return false;
        }
        Medications other = (Medications) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mrrs.persistent.entity.Medications[ id=" + id + " ]";
    }
    
}
