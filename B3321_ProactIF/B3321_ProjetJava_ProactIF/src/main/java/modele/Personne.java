package modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne implements Serializable {
    
    public static final char CIVILITE_MONSIEUR = 'H';
    public static final char CIVILITE_MADAME = 'F';
    
    public Personne() {
    }
    
    public Personne(
        String nom,
        String prenom,
        char civilite,
        String email,
        String motDePasse,
        Date dateNaissance,
        String telephone,
        Adresse adresse) {
        
        this.nom = nom.trim();
        this.prenom = prenom.trim();
        this.civilite = civilite;
        this.email = email.trim();
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone.trim();
        this.adresse = adresse;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    public Long getId() {
        return id;
    }
    
    private String nom;
    
    public String getNom() {
        return nom;
    }
    
    private String prenom;
    
    public String getPrenom() {
        return prenom;
    }
    
    private char civilite;
    
    public char getCivilite() {
        return civilite;
    }
    
    private String email;
    
    @Column(unique = true)
    public String getEmail() {
        return email;
    }
    
    private String motDePasse;
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    public Date getDateNaissance() {
        return dateNaissance;
    }
    
    private String telephone;
    
    public String getTelephone() {
        return telephone;
    }
   
    @Embedded
    private Adresse adresse;
    
    public Adresse getAdresse() {
        return adresse;
    }
}
