package modele;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Employe extends Personne {
    
    public Employe() {
    }
    
    public Employe(
        String nom,
        String prenom,
        char civilite,
        String email,
        String motDePasse,
        Date dateNaissance,
        String telephone,
        Adresse adresse,
        int heureDebutTravail,
        int heureFinTravail){
        
        super(nom, prenom, civilite, email, motDePasse, dateNaissance, telephone, adresse);
        
        this.heureDebutTravail = heureDebutTravail;
        this.heureFinTravail = heureFinTravail;
    }
    
    @Version
    private int version;
    
    private int heureDebutTravail;
    
    public int getHeureDebutTravail() {
        return heureDebutTravail;
    }
    
    private int heureFinTravail;
    
    public int getHeureFinTravail() {
        return heureFinTravail;
    }
    
    @ManyToOne
    private Intervention interventionEnCours;
    
    public Intervention getInterventionEnCours() {
        return interventionEnCours;
    }
    
    public void setInterventionEnCours(Intervention interventionEnCours) {
        this.interventionEnCours = interventionEnCours;
    }
}
