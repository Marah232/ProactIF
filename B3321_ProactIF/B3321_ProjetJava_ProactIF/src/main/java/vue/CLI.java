package vue;

import modele.*;
import service.*;
import dao.JpaUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import util.Saisie;

public class CLI {
    
    public CLI() {
        service = new ServiceConciergerie();
        personneConnectee = null;
    }
    
    public boolean executerCommande() {
        String cmd = Saisie.lireChaine("Saisissez le nom du service à appeler : ");
        cmd = cmd.toLowerCase().trim();
        
        if(cmd.equals("exit")) {
            return false;
        }
        
        if(cmd.equals("?") || cmd.equals("h") || cmd.equals("help")) {
            afficherServices();
            return true;
        }
        
        if(cmd.equals("obtenirtousclients")) {
            List<Client> clients = service.obtenirTousClients();
            if(clients.isEmpty()) {
                afficherLigneSeperation();
                System.out.println("Aucun client à afficher.");
            } else {
                System.out.println(clients.size() + " client(s) existant(s) :");
                for(int i = 0; i < clients.size(); i++) {
                    afficherLigneSeperation();
                    afficherPersonne(clients.get(i));
                }
            }
            afficherLigneSeperation();
            return true;
        }
        
        if(cmd.equals("obtenirtousemployes")) {
            List<Employe> employes = service.obtenirTousEmployes();
            if(employes.isEmpty()) {
                afficherLigneSeperation();
                System.out.println("Aucun employé à afficher.");
            } else {
                System.out.println(employes.size() + " employé(s) existant(s) :");
                for(int i = 0; i < employes.size(); i++) {
                    afficherLigneSeperation();
                    afficherPersonne(employes.get(i));
                }
            }
            afficherLigneSeperation();
            return true;
        }
        
        if(cmd.equals("obtenirtoutesinterventionsdujour")) {
            List<Intervention> interventions = service.obtenirToutesInterventionsDuJour();
            if(interventions.isEmpty()) {
                afficherLigneSeperation();
                System.out.println("Aucun intervention à afficher aujourd'hui.");
            } else {
                System.out.println(interventions.size() + " intervention(s) aujourd'hui :");
                for(int i = 0; i < interventions.size(); i++) {
                    afficherLigneSeperation();
                    afficherIntervention(interventions.get(i));
                }
            }
            afficherLigneSeperation();
            return true;
        }
        
        if(cmd.equals("creeremployes")) {
            personneConnectee = null;
            afficherLigneSeperation();
            try {
                service.creerEmployes();
                System.out.println("Les employés ont bien été créés.");
            }
            catch(ServiceException e) {
                System.out.println("Une erreur est survenue : " + e.getMessage());
            }
            afficherLigneSeperation();
            return true;
        }
        
        if(cmd.equals("authentifierpersonne")) {
            String email = Saisie.lireChaine("Email : ");
            String mdp = Saisie.lireChaine("Mot de passe : ");
            personneConnectee = service.authentifierPersonne(email, mdp);
            afficherLigneSeperation();
            if(personneConnectee != null) {
                if(Client.class.isInstance(personneConnectee)) {
                    System.out.print("Le client");
                } else {
                    System.out.print("L'employé");
                }
                System.out.println(' ' + personneConnectee.getPrenom() + ' '
                    + personneConnectee.getNom() + " a bien été authentifié.");
            } else {
                System.out.println("Echec de l'authentification.");
            }
            afficherLigneSeperation();
            return true;
        }
        
        if(cmd.equals("obtenirinterventionsparclient")) {
            if(personneConnectee == null || !Client.class.isInstance(personneConnectee)) {
                System.out.println("Veuillez d'abord vous authentifier avec un client.");
            } else {
                List<Intervention> interventions = service.obtenirInterventionsParClient((Client)personneConnectee);
                if(interventions.isEmpty()) {
                    afficherLigneSeperation();
                    System.out.println("Le client " + personneConnectee.getPrenom()
                        + ' ' + personneConnectee.getNom() + " n'a aucune intervention.");
                } else {
                    System.out.println("Le client " + personneConnectee.getPrenom()
                        + ' ' + personneConnectee.getNom() + " a "
                        + interventions.size() + " intervention(s) :");
                    for(int i = 0; i < interventions.size(); i++) {
                        afficherLigneSeperation();
                        afficherIntervention(interventions.get(i));
                    }
                }
                afficherLigneSeperation();
            }
            return true;
        }
        
        if(cmd.equals("inscrireclient")) {
            personneConnectee = null;
            
            String stringCivilite = Saisie.lireChaine("Civilité (H/F) : ");
            String nom = Saisie.lireChaine("Nom : ");
            String prenom = Saisie.lireChaine("Prénom : ");
            String email = Saisie.lireChaine("Email : ");
            String mdp = Saisie.lireChaine("Mot de passe : ");
            String stringDateNaissance = Saisie.lireChaine("Date de naissance (JJ/MM/AAAA) : ");
            String telephone = Saisie.lireChaine("Téléphone : ");
            String ville = Saisie.lireChaine("Ville : ");
            int codePostal = Saisie.lireInteger("Code postal : ");
            String rue = Saisie.lireChaine("Numéro de rue et rue : ");
            String complement = Saisie.lireChaine("Complément d'adresse : ");
            
            char civilite = Personne.CIVILITE_MONSIEUR;
            if(stringCivilite.trim().toLowerCase().equals("f")) {
                civilite = Personne.CIVILITE_MADAME;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            afficherLigneSeperation();
            try {
                Client client = new Client(nom, prenom, civilite, email, mdp,
                    sdf.parse(stringDateNaissance), telephone, new Adresse(
                    ville, codePostal, rue, complement));
                
                service.inscrireClient(client);
                System.out.println("Le client a bien été inscrit (numéro : #"
                   + client.getNumClient() + ").");
            }
            catch(Exception e) {
                System.out.println("Une erreur est survenue : " + e.getMessage());
            }
            afficherLigneSeperation();
            return true;
        }
        
        if(cmd.equals("demanderintervention")) {
            if(personneConnectee == null || !Client.class.isInstance(personneConnectee)) {
                System.out.println("Veuillez d'abord vous authentifier avec un client.");
            } else {
                String stringTypeIntervention = Saisie.lireChaine("Type (A=Animal, I=Incident, L=Livraison) : ");
                String description = Saisie.lireChaine("Description : ");
                
                Intervention intervention;
                if(stringTypeIntervention.toLowerCase().trim().equals("i")) {
                    intervention = new InterventionIncident(description);
                } else if(stringTypeIntervention.toLowerCase().trim().equals("l")) {
                    String typeLivraison = Saisie.lireChaine("Type de livraison (colis/lettre...) : ");
                    String entrepriseLivraison = Saisie.lireChaine("Entreprise de livraison : ");
                    intervention = new InterventionLivraison(description, typeLivraison, entrepriseLivraison);
                } else {
                    String typeAnimal = Saisie.lireChaine("Type d'animal : ");
                    intervention = new InterventionAnimal(description, typeAnimal);
                }
                
                try {
                    service.demanderIntervention((Client)personneConnectee, intervention);
                    System.out.println("L'intervention a bien été créée.");
                }
                catch(ServiceException e) {
                    System.out.println("Une erreur est survenue : " + e.getMessage());
                }
            }
            return true;
        }
        
        if(cmd.equals("cloturerintervention")) {
            if(personneConnectee == null || !Employe.class.isInstance(personneConnectee)) {
                System.out.println("Veuillez d'abord vous authentifier avec un employe.");
            } else if(((Employe)personneConnectee).getInterventionEnCours() == null) {
                System.out.println("Cet employé n'a pas d'intervention en cours.");
            } else {
                String stringStatus = Saisie.lireChaine("Status (R=Réalisée, P=Problème) : ");
                String commentaire = Saisie.lireChaine("Commentaire : ");
                
                Intervention intervention = ((Employe)personneConnectee).getInterventionEnCours();
                intervention.setStatus(Intervention.STATUS_REALISEE);
                if(stringStatus.toLowerCase().trim().equals("p")) {
                    intervention.setStatus(Intervention.STATUS_PROBLEME);
                }
                intervention.setCommentaire(commentaire);
                
                try {
                    service.cloturerIntervention(intervention);
                    System.out.println("L'intervention a bien été cloturée.");
                }
                catch(ServiceException e) {
                    System.out.println("Une erreur est survenue : " + e.getMessage());
                }
            }
            return true;
        }
        
        System.out.println("Le service spécifié n'existe pas. "
            + "Tapez ? pour afficher la liste des services disponibles.");
        return true;
    }
    
    public void afficherServices() {
        afficherLigneSeperation();
        System.out.println("Liste des services disponibles :");
        System.out.println(" * inscrireClient : crée un nouveau client");
        System.out.println(" * creerEmployes : crée les employés");
        System.out.println(" * authentifierPersonne : authentifie un client ou un employé");
        System.out.println(" * demanderIntervention : crée une demande d'intervention");
        System.out.println(" * cloturerIntervention : cloture une intervention");
        System.out.println(" * obtenirInterventionsParClient : liste les interventions du client connecté");
        System.out.println(" * obtenirToutesInterventionsDuJour : liste les interventions du jour");
        System.out.println(" * obtenirTousEmployes : liste tous les employés existants");
        System.out.println(" * obtenirTousClients : liste tous les clients existants");
        System.out.println(" * exit : quitte l'application");
        System.out.println(" * ?/h/help : liste les services disponibles");
        afficherLigneSeperation();
    }
    
    private void afficherPersonne(Personne personne) {
        if(Client.class.isInstance(personne)) {
            System.out.println("Numéro client : #" + ((Client)personne).getNumClient());
        }
        if(personne.getCivilite() == Personne.CIVILITE_MONSIEUR) {
            System.out.print("Monsieur ");
        } else {
            System.out.print("Madame ");
        }
        System.out.println(personne.getPrenom() + ' ' + personne.getNom());
        System.out.println("Email : " + personne.getEmail());
        System.out.println("Mot de passe : '" + personne.getMotDePasse() + "'");
        System.out.println("Adresse : " + personne.getAdresse().getRue()
            + ", " + personne.getAdresse().getCodePostal()
            + ' ' + personne.getAdresse().getVille());
        System.out.println("Complément d'adresse : " + personne.getAdresse().getComplement());
        if(Employe.class.isInstance(personne)) {
            Employe employe = (Employe)personne;
            System.out.println("Travaille de " + employe.getHeureDebutTravail()
               + "h à " + employe.getHeureFinTravail() + 'h');
            if(employe.getInterventionEnCours() == null) {
                System.out.println("Pas d'intervention en cours");
            } else {
                Intervention intervention = employe.getInterventionEnCours();
                System.out.println("Intervention pour " + intervention.getClient().getPrenom()
                    + ' ' + intervention.getClient().getNom() + " (#"
                    + intervention.getClient().getNumClient() + ") en cours");
            }
        }
    }
    
    private void afficherIntervention(Intervention intervention) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        System.out.println("Type : " + intervention.getTypeLabel());
        System.out.println("Date : " + df.format(intervention.getDebut()));
        System.out.println("Description : " + intervention.getDescription());
        System.out.println("Client : " + intervention.getClient().getPrenom()
            + ' ' + intervention.getClient().getNom() + " (#"
            + intervention.getClient().getNumClient() + ')');
        System.out.println("Employé : " + intervention.getEmploye().getPrenom()
            + ' ' + intervention.getEmploye().getNom());
        
        if(InterventionAnimal.class.isInstance(intervention)) {
            System.out.println("Type d'animal : " + ((InterventionAnimal)intervention).getTypeAnimal());
        } else if(InterventionLivraison.class.isInstance(intervention)) {
            System.out.println("Type de livraison : " + ((InterventionLivraison)intervention).getTypeLivraison());
            System.out.println("Entreprise de livraison : " + ((InterventionLivraison)intervention).getEntrepriseLivraison());
        }
        
        if(intervention.getStatus() == Intervention.STATUS_EN_COURS) {
            System.out.println("En cours d'intervention");
        } else {
            if(intervention.getStatus() == Intervention.STATUS_REALISEE) {
                System.out.println("Réalisée avec succès");
            } else {
                System.out.println("Réalisée avec un problème");
            }
            System.out.println("Commentaire de l'employé : " + intervention.getCommentaire());
            System.out.println("Cloturée le " + df.format(intervention.getFin()));
        }
    }
    
    private void afficherLigneSeperation() {
        System.out.println("------------------------------------------------------------------------");
    }
    
    private final ServiceConciergerie service;
    private Personne personneConnectee;
    
    public static void main(String[] args) {
        JpaUtil.init();
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Proact'IF v1.0 par Hatim BINANI et Loïc SAOS (B3321)");
        
        CLI cli = new CLI();
        cli.afficherServices();
        while(cli.executerCommande()) {
        }
        
        JpaUtil.destroy();
    }
}
