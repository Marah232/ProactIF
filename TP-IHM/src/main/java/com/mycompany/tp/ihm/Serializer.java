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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Adresse;
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

    public void serializerInterventionEnCoursEmploye(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            JsonObject jsonIntervention = new JsonObject();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonArray jsonListe = new JsonArray();
            Intervention inter = (Intervention) request.getAttribute("Intervention");
            if (inter == null) {
                String nom = (String) request.getAttribute("Nom");
                String prenom = (String) request.getAttribute("Prenom");
                jsonIntervention.addProperty("PresenceIntervention", "NON");
                jsonIntervention.addProperty("Nom_Employe", nom);
                jsonIntervention.addProperty("Prenom_Employe", prenom);
                jsonListe.add(jsonIntervention);
            } 
            else {
                
                
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Adresse A = inter.getClient().getAdresse();
                String Date_Debut = "null";

                if (inter.getDebut() == null) {
                    Date_Debut = "";
                } else {
                    Date_Debut = formatter.format(inter.getDebut());
                }
                
                jsonIntervention.addProperty("Description", inter.getDescription());
                jsonIntervention.addProperty("Nom_Client", inter.getClient().getNom());
                jsonIntervention.addProperty("Prenom_Client", inter.getClient().getPrenom());
                jsonIntervention.addProperty("Nom_Employe", inter.getEmploye().getNom());
                jsonIntervention.addProperty("Prenom_Employe", inter.getEmploye().getPrenom());
                jsonIntervention.addProperty("Label",inter.getTypeLabel());
                jsonIntervention.addProperty("Status", inter.getStatus());
                jsonIntervention.addProperty("Type", inter.getTypeLabel());
                jsonIntervention.addProperty("Date_Debut", Date_Debut);
                jsonIntervention.addProperty("CodePostal", A.getCodePostal());
                jsonIntervention.addProperty("Complement", A.getComplement());
                jsonIntervention.addProperty("Rue", A.getRue());
                jsonIntervention.addProperty("Ville", A.getVille());
                jsonIntervention.addProperty("PresenceIntervention", "OUI");
                jsonListe.add(jsonIntervention);

               
            }
             JsonObject container = new JsonObject();
                container.add("intervention", jsonListe);
                out.println(gson.toJson(container));
        }
    }

    // @TODO
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

    public void serializerInterventions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            List<Intervention> interventions = (List<Intervention>) request.getAttribute("Interventions");
            //List<String> test=new ArrayList<String>();
            /*test.add("ça marche");
            System.out.println(interventions.get(0).getDescription());
             
                 Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println("ICI");
            JsonArray jsonListe = (JsonArray) gson.toJsonTree(interventions);
            
            JsonObject container= new JsonObject();
            container.add("personnes",jsonListe);
             System.out.println("HJGDEHGFUHZERJFHJZGFHKJ");
            out.println(gson.toJson(container));*/
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray jsonListe = new JsonArray();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            String Date_Debut = "null";
            String Date_Fin = "null";

            for (Intervention i : interventions) {
                JsonObject jsonIntervention = new JsonObject();
                Adresse A = i.getClient().getAdresse();

                if (i.getFin() == null) {
                    Date_Fin = "";
                } else {
                    Date_Fin = formatter.format(i.getFin());
                }
                if (i.getDebut() == null) {
                    Date_Debut = "";
                } else {
                    Date_Debut = formatter.format(i.getDebut());
                }
                //

                jsonIntervention.addProperty("Description", i.getDescription());
                jsonIntervention.addProperty("Nom_Client", i.getClient().getNom());
                jsonIntervention.addProperty("Prenom_Client", i.getClient().getPrenom());
                jsonIntervention.addProperty("Nom_Employe", i.getEmploye().getNom());
                jsonIntervention.addProperty("Prenom_Employe", i.getEmploye().getPrenom());
                jsonIntervention.addProperty("Status", i.getStatus());
                jsonIntervention.addProperty("Type", i.getTypeLabel());
                jsonIntervention.addProperty("Date_Debut", Date_Debut);
                jsonIntervention.addProperty("Latitude", A.getLatitude());
                jsonIntervention.addProperty("Longitude", A.getLongitude());
                jsonIntervention.addProperty("Date_Fin", Date_Fin);
                jsonListe.add(jsonIntervention);
            }
            JsonObject container = new JsonObject();
            container.add("interventions", jsonListe);
            out.println(gson.toJson(container));
        }
    }
     public void serializerCloturerIntervention(HttpServletRequest request, HttpServletResponse response) throws IOException{
          response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                out.println(gson.toJson(request.getAttribute("Intervention_cloturee")));
            }
     }
}
