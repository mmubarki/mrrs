/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.persistent.entity;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mussa
 */
@Entity
@Table(name = "User", catalog = "MRRS", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
   @NamedQuery(name = "User.findByReferenceId", query = "SELECT u FROM User u WHERE u.reference_id = :reference_id and u.user_Type = :user_Type"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findLoginMatch", query = "SELECT u FROM User u WHERE u.reference_id = :reference_id and u.password = :password and u.user_Type = :user_Type"),
    @NamedQuery(name = "User.findByUserType", query = "SELECT u FROM User u WHERE u.user_Type = :user_Type"),
    @NamedQuery(name = "User.findByLastlogin", query = "SELECT u FROM User u WHERE u.lastlogin = :lastlogin"),
    @NamedQuery(name="User.findPatient",query = "SELECT p from Patient p WHERE p.id=:id"),
    @NamedQuery(name="User.findPhysician",query = "SELECT p from Physician p WHERE p.id=:id")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name="reference_id", nullable=false)
    private Long reference_id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Password", nullable = false, length = 200)
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "User_Type", nullable = false)
    private int user_Type;
    
    @Column(name = "Last_login")
    @Temporal(TemporalType.DATE)
    private Date lastlogin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UserRoleMap> userRoleMapCollection;
    
    
    @Transient private String name;
    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long reference_Id, String password, int user_Type) {
        this.reference_id =reference_Id;
        this.password = password;
        this.user_Type = user_Type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference_id() {
        return reference_id;
    }

    public void setReference_id(Long reference_id) {
        this.reference_id = reference_id;
    }
    public String getPassword() {
        return password;
        //Hashing.sha256().
    }

    public void setPassword(String password) {
        this.password =Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
        //this.password = password;
    }

    

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public int getUser_Type() {
        return user_Type;
    }

    public void setUser_Type(int user_Type) {
        this.user_Type = user_Type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @XmlTransient
    public Collection<UserRoleMap> getUserRoleMapCollection() {
        return userRoleMapCollection;
    }

    public void setUserRoleMapCollection(Collection<UserRoleMap> userRoleMapCollection) {
        this.userRoleMapCollection = userRoleMapCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        System.out.println("inside user.equals()");
    // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mrrs.persistent.entity.User[ id=" + id + " ]";
    }
    
}
