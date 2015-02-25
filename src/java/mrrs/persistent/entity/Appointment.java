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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mussa
 */
@Entity
@Table(name = "appointment", catalog = "MRRS", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
    @NamedQuery(name = "Appointment.findById", query = "SELECT a FROM Appointment a WHERE a.id = :id"),
    @NamedQuery(name = "Appointment.findByAppointmentDATE", query = "SELECT a FROM Appointment a WHERE a.appointmentDATE = :appointmentDATE")})
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "appointment_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date appointmentDATE;
    @JoinColumn(name = "CLINIC_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Clinic clinicId;
    @JoinColumn(name = "Physician_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Physician physicianID;
    @JoinColumn(name = "RECORD_ID", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private MedicalRecord recordId;

    public Appointment() {
    }

    public Appointment(Integer id) {
        this.id = id;
    }

    public Appointment(Integer id, Date appointmentDATE) {
        this.id = id;
        this.appointmentDATE = appointmentDATE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAppointmentDATE() {
        return appointmentDATE;
    }

    public void setAppointmentDATE(Date appointmentDATE) {
        this.appointmentDATE = appointmentDATE;
    }

    public Clinic getClinicId() {
        return clinicId;
    }

    public void setClinicId(Clinic clinicId) {
        this.clinicId = clinicId;
    }

    public Physician getPhysicianID() {
        return physicianID;
    }

    public void setPhysicianID(Physician physicianID) {
        this.physicianID = physicianID;
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
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mrrs.persistent.entity.Appointment[ id=" + id + " ]";
    }
    
}
