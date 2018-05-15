package dao;

import javax.persistence.*;
import modele.*;

public class PersonneDAO {
    
    public Personne trouverParEmail(String email) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Query qry = em.createQuery("select p from Personne p where lower(p.email)=lower(:email)");
        qry.setParameter("email", email.trim());
        
        try {
           return (Personne)qry.getSingleResult(); 
        }
        catch(NoResultException e) {
            return null;
        }
    }
}
