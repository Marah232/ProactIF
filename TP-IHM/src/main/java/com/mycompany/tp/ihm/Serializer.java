/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.ihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.Employe;
import modele.Intervention;
import modele.Personne;

/**
 *
 * @author User
 */
public class Serializer {

    public void serializerPersonne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getAttribute("user") == null) {
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Personne p = (Personne) request.getAttribute("user");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                out.println(gson.toJson("null"));
            }

        } else {
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Personne p = (Personne) request.getAttribute("user");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (p instanceof Employe) {
                    out.println(gson.toJson("employe"));
                }
                if (p instanceof Client) {
                    out.println(gson.toJson("client"));
                }
            }
        }

    }

    public void serializerNomEmploye(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String nom = (String) request.getAttribute("NomEmploye");
            String prenom = (String) request.getAttribute("PrenomEmploye");
            List noms = new ArrayList();
            noms.add(nom);
            noms.add(prenom);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray jsonListe = (JsonArray) gson.toJsonTree(noms);
            JsonObject container = new JsonObject();
            container.add("noms", jsonListe);
            out.println(gson.toJson(container));

        }
    }
    
    // @TODO
    public void serializerInterventions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
         try (PrintWriter out = response.getWriter()) {
            List<Intervention> interventions= new ArrayList<>();
            interventions.add((Intervention)request.getAttribute("Intervention"));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray jsonListe = (JsonArray) gson.toJsonTree(interventions);
            JsonObject container = new JsonObject();
            container.add("interventions", jsonListe);
            System.out.println("==========================================");
            out.println(gson.toJson(container));
            
        }
    }
     public void serializerInscription(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (request.getAttribute("client") == null) {
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                out.println(gson.toJson(false));
            }

        } else {
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                out.println(gson.toJson(true));
            }
        }

    }
}
