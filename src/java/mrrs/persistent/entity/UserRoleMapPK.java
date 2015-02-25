/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.persistent.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mussa
 */
@Embeddable
public class UserRoleMapPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "userID", nullable = false)
    private Long userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "roleID", nullable = false)
    private int roleID;

    public UserRoleMapPK() {
    }

    public UserRoleMapPK(Long userID, int roleID) {
        this.userID = userID;
        this.roleID = roleID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Long) userID;
        hash += (int) roleID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoleMapPK)) {
            return false;
        }
        UserRoleMapPK other = (UserRoleMapPK) object;
        if (this.userID != other.userID) {
            return false;
        }
        if (this.roleID != other.roleID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mrrs.persistent.entity.UserRoleMapPK[ userID=" + userID + ", roleID=" + roleID + " ]";
    }
    
}
