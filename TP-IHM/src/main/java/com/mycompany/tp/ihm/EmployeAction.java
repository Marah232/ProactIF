/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.ihm;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import modele.Employe;
import modele.Intervention;
import modele.Personne;
import service.ServiceConciergerie;
import service.ServiceException;

/**
 *
 * @author User
 */
public class EmployeAction implements Action {
     @Override
    public void excecute(HttpServletRequest request) {
      ServiceConciergerie service = new ServiceConciergerie();
        
       List<Intervention> Interventions= service.obtenirToutesInterventionsDuJour();
    
        
        request.setAttribute("Interventions",Interventions );      

    }
    /*public void cloturer_intervention(HttpServletRequest request) {
      ServiceConciergerie service = new ServiceConciergerie();
      Employe e=(Employe) request.getAttribute("Employe");
       System.out.println("djkghjkerge");
      System.out.println(e.getNom());
        Intervention i=e.getInterventionEnCours();
       
        char status=request.getParameter("statusIntervention").charAt(0);
        String commentaires=request.getParameter("commentaires");
        i.setStatus(status);
        i.setCommentaire(commentaires);
         try {
             service.cloturerIntervention(i);
             request.setAttribute("Intervention_cloturee","true" );    
         } catch (ServiceException ex) {
             request.setAttribute("Intervention_cloturee","false" );
             Logger.getLogger(EmployeAction.class.getName()).log(Level.SEVERE, null, ex);
         }
          

    }*/
    
}
