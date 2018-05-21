/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.ihm;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import modele.Client;
import modele.Intervention;
import modele.InterventionAnimal;
import modele.InterventionIncident;
import modele.InterventionLivraison;
import service.ServiceConciergerie;
import service.ServiceException;

/**
 *
 * @author edelahaye
 */
public class InterventionAction implements Action {

    @Override
    public void excecute(HttpServletRequest request) {
        String intervention = request.getParameter("intervention");
        String description = request.getParameter("description");
        String typeAnimal = request.getParameter("espece");
        String type = request.getParameter("type");
        String livraison = request.getParameter("livraison");

        List<String> errors = new LinkedList<>();

        if (description.equals("")) {
            errors.add("Merci de mettre un description");
            request.setAttribute("errors", errors);
            return;
        }

        Intervention inter = null;

        switch (intervention) {
            case "animal": {
                if (typeAnimal != null && !typeAnimal.equals("")) {
                    System.out.println("animaaaaaaaaaaaaaaaaaaaaaaaaaaal = " + typeAnimal);
                    System.out.println("deeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeesc = " + description);
                    inter = new InterventionAnimal(description, typeAnimal);
                } else {
                    errors.add("Merci de préciser l'espèce de l'animal.");
                    request.setAttribute("errors", errors);

                    return;
                }
                try {
                    ServiceConciergerie sc = new ServiceConciergerie();
                    sc.demanderIntervention((Client) request.getAttribute("sessionUser"), inter);
                } catch (ServiceException e) {
                    errors.add("PROBLEME " + e.getMessage());
                }
                request.setAttribute("errors", errors);
                break;
            }
            case "incident": {
                inter = new InterventionIncident();
                inter.setClient((Client) request.getAttribute("sessionUser"));
                inter = new InterventionIncident();
                break;
            }
            case "livraison": {
                inter = new InterventionLivraison();
                inter.setClient((Client) request.getAttribute("sessionUser"));
                break;
            }
            default:
                break;
        }
    }

}
