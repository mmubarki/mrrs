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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "UserRoleMap", catalog = "MRRS", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRoleMap.findAll", query = "SELECT u FROM UserRoleMap u"),
    @NamedQuery(name = "UserRoleMap.findByUserID", query = "SELECT u FROM UserRoleMap u WHERE u.userRoleMapPK.userID = :userID"),
    @NamedQuery(name = "UserRoleMap.findByRoleID", query = "SELECT u FROM UserRoleMap u WHERE u.userRoleMapPK.roleID = :roleID"),
    @NamedQuery(name = "UserRoleMap.findByStartDate", query = "SELECT u FROM UserRoleMap u WHERE u.startDate = :startDate"),
    @NamedQuery(name = "UserRoleMap.findByEndDate", query = "SELECT u FROM UserRoleMap u WHERE u.endDate = :endDate")})
public class UserRoleMap implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserRoleMapPK userRoleMapPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @JoinColumn(name = "userID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "roleID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Role role;

    public UserRoleMap() {
    }

    public UserRoleMap(UserRoleMapPK userRoleMapPK) {
        this.userRoleMapPK = userRoleMapPK;
    }

    public UserRoleMap(UserRoleMapPK userRoleMapPK, Date startDate) {
        this.userRoleMapPK = userRoleMapPK;
        this.startDate = startDate;
    }

    public UserRoleMap(Long userID, int roleID) {
        this.userRoleMapPK = new UserRoleMapPK(userID, roleID);
    }

    public UserRoleMapPK getUserRoleMapPK() {
        return userRoleMapPK;
    }

    public void setUserRoleMapPK(UserRoleMapPK userRoleMapPK) {
        this.userRoleMapPK = userRoleMapPK;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleMapPK != null ? userRoleMapPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoleMap)) {
            return false;
        }
        UserRoleMap other = (UserRoleMap) object;
        if ((this.userRoleMapPK == null && other.userRoleMapPK != null) || (this.userRoleMapPK != null && !this.userRoleMapPK.equals(other.userRoleMapPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mrrs.persistent.entity.UserRoleMap[ userRoleMapPK=" + userRoleMapPK + " ]";
    }
    
}
