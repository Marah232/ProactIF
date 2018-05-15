package modele;

import javax.persistence.*;

@Entity
public class InterventionLivraison extends Intervention {
    
    public InterventionLivraison() {
    }
    
    public InterventionLivraison(String description, String typeLivraison, String entrepriseLivraison) {
        super(description);
        
        this.typeLivraison = typeLivraison;
        this.entrepriseLivraison = entrepriseLivraison;
    }
    
    @Override
    public String getTypeLabel() {
        return "Livraison";
    }
    
    private String typeLivraison;
    
    public String getTypeLivraison() {
        return typeLivraison;
    }
    
    private String entrepriseLivraison;
    
    public String getEntrepriseLivraison() {
        return entrepriseLivraison;
    }
}
