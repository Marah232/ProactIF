package dao;

import java.util.List;
import javax.persistence.*;
import modele.*;

public class ClientDAO {
    
    public void creer(Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        em.persist(c);
    }
    
    public List<Client> trouverTous() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Query qry = em.createQuery("select c from Client c");
        
        return (List<Client>)qry.getResultList();
    }
}
