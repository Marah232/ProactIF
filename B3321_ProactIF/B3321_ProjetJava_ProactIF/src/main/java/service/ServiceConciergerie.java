package service;

import com.google.maps.model.LatLng;
import modele.*;
import dao.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.List;
import util.*;
import java.util.Date;

public class ServiceConciergerie {
    
    public static final String EMAIL_SOCIETE = "contact@proact.if";
    public static final String TELEPHONE_SOCIETE = "0102030405";
    
    public void inscrireClient(Client client) throws ServiceException {
        JpaUtil.creerEntityManager();
        
        try {
            JpaUtil.ouvrirTransaction();
            
            PersonneDAO personneDAO = new PersonneDAO();
            if(personneDAO.trouverParEmail(client.getEmail()) != null) {
                throw new Exception("L'adresse email est déjà utilisée.");
            }
            
            LatLng pos = GeoTest.getLatLng(client.getAdresse().getRue() + ", "
                + client.getAdresse().getCodePostal() + " " + client.getAdresse().getVille());
            
            if(pos == null) {
                throw new Exception("Impossible de trouver les coordonnées GPS du client.");
            }
            
            client.getAdresse().setLatitude(pos.lat);
            client.getAdresse().setLongitude(pos.lng);
            
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.creer(client);

            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            JpaUtil.annulerTransaction();
            
            Notification.envoyerMail(EMAIL_SOCIETE,
                client.getEmail(),
                "Echec de l'inscription",
                "Bonjour " + client.getPrenom() + ",\n"
                + "Votre inscription au service PROACT'IF a malencontreusement échoué... "
                + "Merci de recommencer ultérieurement."
            );
                        
            throw new ServiceException(e);
        }
        finally {
            JpaUtil.fermerEntityManager();
        }
        
        Notification.envoyerMail(EMAIL_SOCIETE,
            client.getEmail(),
            "Confirmation d'inscription",
            "Bonjour " + client.getPrenom() + ",\n"
            + "Nous vous confirmons votre inscription au service PROACT'IF. "
            + "Votre numéro client est : " + client.getNumClient() + "."
        );
    }
    
    public void creerEmployes() throws ServiceException {
        JpaUtil.creerEntityManager();
        
        try {
            JpaUtil.ouvrirTransaction();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            List<Employe> employes = new ArrayList<Employe>();

            employes.add(new Employe("BINANI", "Hatim", Personne.CIVILITE_MONSIEUR,
                "hatim.binani@insa-lyon.fr", "Emp1", sdf.parse("31/10/1997"),
                "0669138385", new Adresse("VILLEURBANNE", 69621, "20 Avenue Albert Einstein", "Residence I527"),
                8, 18));
            
            employes.add(new Employe("VIANEY", "Antonin", Personne.CIVILITE_MONSIEUR,
                "antonin.vianey@insa-lyon.fr", "Emp2", sdf.parse("26/04/1997"),
                "0656329875", new Adresse("Lyon", 69003, "12-14 Rue des Cuirassiers", ""),
                10, 20));
            
            employes.add(new Employe("MEGZARI", "Mohammed", Personne.CIVILITE_MONSIEUR,
                "mohammed.megzari@insa-lyon.fr", "Emp3", sdf.parse("14/07/1996"),
                "0725326541", new Adresse("Lyon", 69005, "11 Montée Nicolas de Lange", ""),
                8, 18));
            
            employes.add(new Employe("SAOS", "Marine", Personne.CIVILITE_MADAME,
                "marine.saos@gmail.com", "Emp4", sdf.parse("12/05/1999"),
                "0623457821", new Adresse("Lyon", 69002, "Cours Charlemagne", ""),
                6, 16));
            
            employes.add(new Employe("MICHU", "Martine", Personne.CIVILITE_MADAME,
                "martine.michu.pro@gmail.com", "Emp5", sdf.parse("17/08/1975"),
                "0756234124", new Adresse("VENISSIEUX", 69200, "7 Avenue d'Oschatz", ""),
                12, 23));
            
            PersonneDAO personneDAO = new PersonneDAO();
            EmployeDAO employeDAO = new EmployeDAO();
            
            for(int i = 0; i < employes.size(); i++) {
                Employe e = employes.get(i);
                
                if(personneDAO.trouverParEmail(e.getEmail()) != null) {
                    throw new Exception("La personne " + e.getEmail() + " existe déjà.");
                }
                
                LatLng pos = GeoTest.getLatLng(e.getAdresse().getRue() + ", "
                    + e.getAdresse().getCodePostal() + " " + e.getAdresse().getVille());
                e.getAdresse().setLatitude(pos.lat);
                e.getAdresse().setLongitude(pos.lng);

                employeDAO.creer(e);
            }

            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            JpaUtil.annulerTransaction();
            throw new ServiceException(e);
        }
        finally {
            JpaUtil.fermerEntityManager();
        }
    }
    
    public Personne authentifierPersonne(String email, String motDePasse) {
        JpaUtil.creerEntityManager();
        
        PersonneDAO personneDAO = new PersonneDAO();
        Personne personne = personneDAO.trouverParEmail(email);
        
        JpaUtil.fermerEntityManager();
        
        if(personne == null || !personne.getMotDePasse().equals(motDePasse)) {
            return null;
        } else {
            return personne;
        }
    }
    
    public void demanderIntervention(Client client, Intervention intervention) throws ServiceException {
        JpaUtil.creerEntityManager();
        
        intervention.setClient(client);

        if(intervention.getStatus() != Intervention.STATUS_EN_COURS
            || intervention.getEmploye() != null) {
            throw new ServiceException("Cette intervention a déjà été créée.");
        }
        
        LatLng posIntervention = new LatLng(
            intervention.getClient().getAdresse().getLatitude(),
            intervention.getClient().getAdresse().getLongitude()
        );
        
        int heureIntervention = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        intervention.setDebut(new Date());
        
        boolean continuerRecherche = true;
        
        while(continuerRecherche) {
            
            try {
                JpaUtil.ouvrirTransaction();

                EmployeDAO employeDAO = new EmployeDAO();
                InterventionDAO interventionDAO = new InterventionDAO();

                List<Employe> employesDisponibles = employeDAO.trouverDisponibles();

                Employe employeLePlusProche = null;
                double tempsLePlusCourt = 9999999999.0;

                for(int i = 0; i < employesDisponibles.size(); i++) {
                    Employe emp = employesDisponibles.get(i);
                    
                    if(emp.getHeureDebutTravail() <= heureIntervention
                        && emp.getHeureFinTravail() > heureIntervention) {

                        LatLng posEmploye = new LatLng(
                            emp.getAdresse().getLatitude(),
                            emp.getAdresse().getLongitude()
                        );

                        double temps = GeoTest.getTripDurationByBicycleInMinute(posIntervention, posEmploye);

                        if(temps < tempsLePlusCourt) {
                            employeLePlusProche = emp;
                            tempsLePlusCourt = temps;
                        }
                    }
                }
                
                if(employeLePlusProche != null) {
                    
                    employeLePlusProche.setInterventionEnCours(intervention);
                    intervention.setEmploye(employeLePlusProche);

                    employeDAO.mettreAJour(employeLePlusProche);
                    interventionDAO.creer(intervention);
                    
                    JpaUtil.validerTransaction();
                }
                
                continuerRecherche = false;
            }
            catch(Exception e) {
                JpaUtil.annulerTransaction();
                intervention.setEmploye(null);
            }
        }
        
        JpaUtil.fermerEntityManager();
        
        if(intervention.getEmploye() == null) {
            throw new ServiceException("Aucun employe n'est disponible actuellement.");
        }
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        double distanceKm = GeoTest.getFlightDistanceInKm(
            posIntervention, new LatLng(
                intervention.getEmploye().getAdresse().getLatitude(),
                intervention.getEmploye().getAdresse().getLongitude()
        ));
        
        Notification.envoyerSMS(TELEPHONE_SOCIETE,
            intervention.getClient().getTelephone(),
            "Intervention " + intervention.getTypeLabel() + " demandée le "
            + df.format(intervention.getDebut()) + " pour " + intervention.getClient().getPrenom()
            + " " + intervention.getClient().getNom() + " (#" + intervention.getClient().getNumClient()
            + "), " + intervention.getClient().getAdresse().getRue() + ", "
            + intervention.getClient().getAdresse().getCodePostal()
            + intervention.getClient().getAdresse().getVille() + " ("
            + distanceKm + " km) : " + intervention.getDescription()
        );
    }
    
    public void cloturerIntervention(Intervention intervention) throws ServiceException {
        JpaUtil.creerEntityManager();
        
        if(intervention.getStatus() == Intervention.STATUS_EN_COURS) {
            throw new ServiceException("Le status de l'intervention vaut toujours STATUS_EN_COURS.");
        }
        if(intervention.getFin() != null) {
            throw new ServiceException("L'intervention est déjà cloturée.");
        }
        
        intervention.setFin(new Date());
        
        Employe employe =  intervention.getEmploye();
        employe.setInterventionEnCours(null);
        
        try {
            JpaUtil.ouvrirTransaction();
            
            InterventionDAO interventionDAO = new InterventionDAO();
            EmployeDAO employeDAO = new EmployeDAO();
            
            interventionDAO.mettreAJour(intervention);
            employeDAO.mettreAJour(employe);
            
            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            JpaUtil.annulerTransaction();
            throw new ServiceException(e);
        }
        finally {
            JpaUtil.fermerEntityManager();
        }
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        String commentaire = "";
        if(!intervention.getCommentaire().isEmpty()) {
            commentaire = " Commentaire : " + intervention.getCommentaire();
        }
        
        String status = "succès";
        if(intervention.getStatus() == Intervention.STATUS_PROBLEME) {
            status = "un problème";
        }
        
        Notification.envoyerSMS(TELEPHONE_SOCIETE,
            intervention.getClient().getTelephone(), 
            "Bonjour " + intervention.getClient().getPrenom() + " "
            + intervention.getClient().getNom() + ", votre intervention "
            + intervention.getTypeLabel() + " du " + df.format(intervention.getDebut())
            + " a été effectuée par un de nos agents avec " + status
            + "." + commentaire
        );
    }
    
    public List<Intervention> obtenirToutesInterventionsDuJour() {
        JpaUtil.creerEntityManager();
        
        InterventionDAO interventionDAO = new InterventionDAO();
        List<Intervention> interventions = interventionDAO.obtenirToutesDuJour();
        
        JpaUtil.fermerEntityManager();
        
        return interventions;
    }
    
    public List<Intervention> obtenirInterventionsParClient(Client client) {
        JpaUtil.creerEntityManager();
        
        InterventionDAO interventionDAO = new InterventionDAO();
        List<Intervention> interventions = interventionDAO.trouverParClient(client.getId());
        
        JpaUtil.fermerEntityManager();
        
        return interventions;
    }
    
    public List<Employe> obtenirTousEmployes() {
        JpaUtil.creerEntityManager();
        
        EmployeDAO employeDAO = new EmployeDAO();
        List<Employe> employes = employeDAO.trouverTous();
        
        JpaUtil.fermerEntityManager();
        
        return employes;
    }
    
    public List<Client> obtenirTousClients() {
        JpaUtil.creerEntityManager();
        
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.trouverTous();
        
        JpaUtil.fermerEntityManager();
        
        return clients;
    }
}
