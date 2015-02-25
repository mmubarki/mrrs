/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrrs.logic.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mrrs.persistent.entity.Medications;

/**
 *
 * @author mussa
 */
@Stateless
public class MedicationsFacade extends AbstractFacade<Medications> {
    @PersistenceContext(unitName = "mrrsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MedicationsFacade() {
        super(Medications.class);
    }
    
}
