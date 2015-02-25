/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.logic.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import mrrs.persistent.entity.Patient;
import mrrs.persistent.entity.Role;

/**
 *
 * @author mussa
 */
@Stateless
public class RoleFacade extends AbstractFacade<Role> {
    @PersistenceContext(unitName = "mrrsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
    }
    
    public List<Role> findAllRole ()
    {
        System.out.println("@@inside roleFacade findAllRole");
        try
       {
        TypedQuery<Role> queryFindAll =
        em.createNamedQuery("Role.findAll", Role.class);
  
       /* Query queryFindLoginMatch = em.createNamedQuery("findLoginMatch");*/
        System.out.println("b4 getResultList");
        return  queryFindAll.getResultList();
        }
       catch(Exception e)
       {
           System.out.println("in facase exception:"+e.getMessage());
           return null;
       }
    }
}
