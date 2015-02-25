/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.persistent.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mussa
 */
@Entity
@Table(name = "CLINIC", catalog = "MRRS", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clinic.findAll", query = "SELECT c FROM Clinic c"),
    @NamedQuery(name = "Clinic.findById", query = "SELECT c FROM Clinic c WHERE c.id = :id"),
    @NamedQuery(name = "Clinic.findByName", query = "SELECT c FROM Clinic c WHERE c.name = :name"),
    @NamedQuery(name = "Clinic.findByAddress", query = "SELECT c FROM Clinic c WHERE c.address = :address"),
    @NamedQuery(name = "Clinic.findByCity", query = "SELECT c FROM Clinic c WHERE c.city = :city"),
    @NamedQuery(name = "Clinic.findByState", query = "SELECT c FROM Clinic c WHERE c.state = :state"),
    @NamedQuery(name = "Clinic.findByZip", query = "SELECT c FROM Clinic c WHERE c.zip = :zip")})
public class Clinic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "address", nullable = false, length = 100)
    private String address;
    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;
    @Size(max = 100)
    @Column(name = "state", length = 100)
    private String state;
    @Size(max = 100)
    @Column(name = "zip", length = 100)
    private String zip;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinicId")
    private Collection<MedicalRecord> medicalRecordCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinicId")
    private Collection<Appointment> appointmentCollection;

    public Clinic() {
    }

    public Clinic(Integer id) {
        this.id = id;
    }

    public Clinic(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @XmlTransient
    public Collection<MedicalRecord> getMedicalRecordCollection() {
        return medicalRecordCollection;
    }

    public void setMedicalRecordCollection(Collection<MedicalRecord> medicalRecordCollection) {
        this.medicalRecordCollection = medicalRecordCollection;
    }

    @XmlTransient
    public Collection<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(Collection<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
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
        if (!(object instanceof Clinic)) {
            return false;
        }
        Clinic other = (Clinic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mrrs.persistent.entity.Clinic[ id=" + id + " ]";
    }
    
}
