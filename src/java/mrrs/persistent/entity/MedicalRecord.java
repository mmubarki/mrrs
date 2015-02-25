/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.persistent.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mussa
 */
@Entity
@Table(name = "medical_record", catalog = "MRRS", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicalRecord.findAll", query = "SELECT m FROM MedicalRecord m"),
    @NamedQuery(name = "MedicalRecord.findById", query = "SELECT m FROM MedicalRecord m WHERE m.id = :id"),
    @NamedQuery(name = "MedicalRecord.findByReport", query = "SELECT m FROM MedicalRecord m WHERE m.report = :report"),
    @NamedQuery(name = "MedicalRecord.findByRecipe", query = "SELECT m FROM MedicalRecord m WHERE m.recipe = :recipe"),
    @NamedQuery(name = "MedicalRecord.findByRecipeDATE", query = "SELECT m FROM MedicalRecord m WHERE m.recipeDATE = :recipeDATE"),
    @NamedQuery(name = "MedicalRecord.findByOriginalID", query = "SELECT m FROM MedicalRecord m WHERE m.originalID = :originalID")})
public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "report", nullable = false, length = 1000)
    private String report;
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
    @Column(name = "original_ID")
    private Integer originalID;
    @JoinColumn(name = "Patient_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientID;
    @JoinColumn(name = "PHYSICIAN_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Physician physicianId;
    @JoinColumn(name = "CLINIC_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Clinic clinicId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
    private Collection<Medications> medicationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
    private Collection<Appointment> appointmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
    private Collection<MedicalPicture> medicalPictureCollection;

    public MedicalRecord() {
    }

    public MedicalRecord(Integer id) {
        this.id = id;
    }

    public MedicalRecord(Integer id, String report, String recipe, Date recipeDATE) {
        this.id = id;
        this.report = report;
        this.recipe = recipe;
        this.recipeDATE = recipeDATE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
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

    public Integer getOriginalID() {
        return originalID;
    }

    public void setOriginalID(Integer originalID) {
        this.originalID = originalID;
    }

    public Patient getPatientID() {
        return patientID;
    }

    public void setPatientID(Patient patientID) {
        this.patientID = patientID;
    }

    public Physician getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(Physician physicianId) {
        this.physicianId = physicianId;
    }

    public Clinic getClinicId() {
        return clinicId;
    }

    public void setClinicId(Clinic clinicId) {
        this.clinicId = clinicId;
    }

    @XmlTransient
    public Collection<Medications> getMedicationsCollection() {
        return medicationsCollection;
    }

    public void setMedicationsCollection(Collection<Medications> medicationsCollection) {
        this.medicationsCollection = medicationsCollection;
    }

    @XmlTransient
    public Collection<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(Collection<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }

    @XmlTransient
    public Collection<MedicalPicture> getMedicalPictureCollection() {
        return medicalPictureCollection;
    }

    public void setMedicalPictureCollection(Collection<MedicalPicture> medicalPictureCollection) {
        this.medicalPictureCollection = medicalPictureCollection;
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
        if (!(object instanceof MedicalRecord)) {
            return false;
        }
        MedicalRecord other = (MedicalRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mrrs.persistent.entity.MedicalRecord[ id=" + id + " ]";
    }
    
}
