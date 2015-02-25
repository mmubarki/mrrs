/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.logic.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mrrs.persistent.entity.UserRoleMap;

/**
 *
 * @author mussa
 */
@Stateless
public class UserRoleMapFacade extends AbstractFacade<UserRoleMap> {
    @PersistenceContext(unitName = "mrrsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRoleMapFacade() {
        super(UserRoleMap.class);
    }
    
}
