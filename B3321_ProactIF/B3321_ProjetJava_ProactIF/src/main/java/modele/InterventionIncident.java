package modele;

import javax.persistence.*;

@Entity
public class InterventionIncident extends Intervention {
    
    public InterventionIncident() {
    }
    
    public InterventionIncident(String description) {
        super(description);
    }
    
    @Override
    public String getTypeLabel() {
        return "Incident";
    }
}
