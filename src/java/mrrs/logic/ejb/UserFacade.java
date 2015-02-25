/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.logic.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import mrrs.persistent.entity.Patient;
import mrrs.persistent.entity.Physician;
import mrrs.persistent.entity.User;

/**
 *
 * @author mussa
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "mrrsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    public Patient findPatient (User user)
    {
        System.out.println("@@inside userFacade findPatient:"+user.getReference_id());
        try
       {
        TypedQuery<Patient> queryFindPatient =
        em.createNamedQuery("User.findPatient", Patient.class);
  
       /* Query queryFindLoginMatch = em.createNamedQuery("findLoginMatch");*/
        queryFindPatient.setParameter("id",user.getReference_id());
        System.out.println("b4 getResultList");
        Patient patient =(Patient) queryFindPatient.getSingleResult();
        user.setName(patient.getFirstName()+" "+patient.getLastName());
        //patient.setUser(user);
        return patient;
        }
       catch(Exception e)
       {
           System.out.println("in facase exception:"+e.getMessage());
           return null;
       }
    }
    public User findLoginMatch(User user) {
        System.out.println("@@inside userFacade isLoginMatch:"+user.getReference_id()+" "+user.getPassword()+" "+user.getUser_Type());
       try
       {
        TypedQuery<User> queryFindLoginMatch =
        em.createNamedQuery("User.findLoginMatch", User.class);
  
       /* Query queryFindLoginMatch = em.createNamedQuery("findLoginMatch");*/       
        queryFindLoginMatch.setParameter("reference_id",user.getReference_id());
        queryFindLoginMatch.setParameter("password", user.getPassword());
        queryFindLoginMatch.setParameter("user_Type", user.getUser_Type());
        System.out.println(queryFindLoginMatch.toString());
        System.out.println("b4 getResultList");
        return (User) queryFindLoginMatch.getSingleResult();
        }
       catch(Exception e)
       {
           System.out.println("in facase exception:"+e.getMessage());
           return null;
       }
    }

    public Physician findPhysician(User user) {
        System.out.println("@@inside userFacade findPhysician:"+user.getReference_id());
        try
       {
        TypedQuery<Physician> queryFindPatient =
        em.createNamedQuery("User.findPhysician", Physician.class);
  
       /* Query queryFindLoginMatch = em.createNamedQuery("findLoginMatch");*/
        queryFindPatient.setParameter("id",user.getReference_id());
        System.out.println("b4 getResultList");
        Physician physician =(Physician) queryFindPatient.getSingleResult();
        user.setName(physician.getName());
        //physician.setUser(user);
        return physician;
        }
       catch(Exception e)
       {
           System.out.println("in facase exception:"+e.getMessage());
           return null;
       }
    }

}
