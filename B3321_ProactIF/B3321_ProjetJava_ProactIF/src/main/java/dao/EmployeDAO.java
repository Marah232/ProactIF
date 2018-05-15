package dao;

import java.util.List;
import javax.persistence.*;
import modele.*;

public class EmployeDAO {
    
    public void creer(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        em.persist(e);
    }
    
    public void mettreAJour(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        em.merge(e);
    }
    
    public List<Employe> trouverDisponibles() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Query qry = em.createQuery("select e from Employe e where e.interventionEnCours is null");
        
        return (List<Employe>)qry.getResultList();
    }
    
    public List<Employe> trouverTous() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Query qry = em.createQuery("select e from Employe e");
        
        return (List<Employe>)qry.getResultList();
    }
}
