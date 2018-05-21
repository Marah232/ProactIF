/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.ihm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import modele.Adresse;
import modele.Client;


import service.ServiceConciergerie;
import service.ServiceException;

/**
 *
 * @author User
 */
public class RegisterAction implements Action {
     @Override
    public void excecute(HttpServletRequest request) {
        ServiceConciergerie service = new ServiceConciergerie();
        char civilite = request.getParameter("civilite").charAt(0);
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String adresseMail= request.getParameter("adresseMail");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date DatedeNaissance=new Date();
         try {
             DatedeNaissance = sdf.parse(request.getParameter("DatedeNaissance"));
         } catch (ParseException ex) {
             Logger.getLogger(RegisterAction.class.getName()).log(Level.SEVERE, null, ex);
         }

        String Ville = request.getParameter("Ville");
        int CodePostal= Integer.parseInt(request.getParameter("CodePostal"));
        String RueEtNumero= request.getParameter("RueEtNumero");
    
        String ComplementAdresse= request.getParameter("ComplementAdresse");
        String MotPasse = request.getParameter("MotPasse");
        
        Adresse A=new Adresse(Ville,CodePostal,RueEtNumero,ComplementAdresse);
       
        Client c= new Client(nom,prenom,civilite,adresseMail,MotPasse,DatedeNaissance,telephone,A);
        
         try {
             service.inscrireClient(c);
         } catch (ServiceException ex) {
             Logger.getLogger(RegisterAction.class.getName()).log(Level.SEVERE, null, ex);
         }
        request.setAttribute("client", c);
        /*System.out.println(c.getNom());
        System.out.println(c.getPrenom());
        System.out.println(c.getCivilite());
        System.out.println(c.getEmail());
        System.out.println(c.getMotDePasse());
        System.out.println(c.getDateNaissance());
        System.out.println(c.getTelephone());*/
        
        
        

    }
    
}
