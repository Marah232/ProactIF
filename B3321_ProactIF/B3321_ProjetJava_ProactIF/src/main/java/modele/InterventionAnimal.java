package modele;

import javax.persistence.*;

@Entity
public class InterventionAnimal extends Intervention {
    
    public InterventionAnimal() {
    }
    
    public InterventionAnimal(String description, String typeAnimal) {
        super(description);
        
        this.typeAnimal = typeAnimal;
    }
    
    @Override
    public String getTypeLabel() {
        return "Animal";
    }
    
    private String typeAnimal;
    
    public String getTypeAnimal() {
        return typeAnimal;
    }
}
