package modele;

import javax.persistence.*;

@Embeddable
public class Adresse {
    
    public Adresse() {
    }
    
    public Adresse(
        String ville,
        int codePostal,
        String rue,
        String complement) {
        
        this.ville = ville;
        this.codePostal = codePostal;
        this.rue = rue;
        this.complement = complement;
    }
    
    private String ville;
    
    public String getVille() {
        return ville;
    }
    
    private int codePostal;
    
    public int getCodePostal() {
        return codePostal;
    }
    
    private String rue;
    
    public String getRue() {
        return rue;
    }
    
    private String complement;
    
    public String getComplement() {
        return complement;
    }
    
    private double latitude;
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    private double longitude;
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
