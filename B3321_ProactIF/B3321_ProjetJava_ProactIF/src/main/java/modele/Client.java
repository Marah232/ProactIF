package modele;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Client extends Personne {
    
    public Client() {
    }
    
    public Client(
        String nom,
        String prenom,
        char civilite,
        String email,
        String motDePasse,
        Date dateNaissance,
        String telephone,
        Adresse adresse) {
        
        super(nom, prenom, civilite, email, motDePasse, dateNaissance, telephone, adresse);
    }
    
    public Long getNumClient() {
        return super.getId();
    }
}
