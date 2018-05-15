package dao;

import java.util.List;
import javax.persistence.*;
import modele.*;

public class InterventionDAO {
    
    public void creer(Intervention i) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        em.persist(i);
    }
    
    public void mettreAJour(Intervention i) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        em.merge(i);
    }
    
    public List<Intervention> trouverParClient(long idClient) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Query qry = em.createQuery("select i from Intervention i where i.client.id=:idClient");
        qry.setParameter("idClient", idClient);
        
        return (List<Intervention>)qry.getResultList();
    }
    
    public List<Intervention> obtenirToutesDuJour() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Query qry = em.createQuery("select i from Intervention i where "
            + "EXTRACT(YEAR i.debut)=EXTRACT(YEAR CURRENT_DATE) "
            + " and EXTRACT(MONTH i.debut)=EXTRACT(MONTH CURRENT_DATE)"
            + " and EXTRACT(DAY i.debut)=EXTRACT(DAY CURRENT_DATE)");
        
        return (List<Intervention>)qry.getResultList();
    }
}
