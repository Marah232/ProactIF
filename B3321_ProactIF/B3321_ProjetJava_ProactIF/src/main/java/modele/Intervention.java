package modele;

import java.util.Date;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Intervention {
    
    public static final char STATUS_EN_COURS = 'E';
    public static final char STATUS_REALISEE = 'R';
    public static final char STATUS_PROBLEME = 'P';
    
    public Intervention() {
    }
    
    public Intervention(String description) {
        this.status = STATUS_EN_COURS;
        this.status = STATUS_EN_COURS;
        this.description = description;
    }
    
    public abstract String getTypeLabel();
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    
    public long getId() {
        return id;
    }

    @ManyToOne
    protected Client client;
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    @ManyToOne
    protected Employe employe;
    
    public Employe getEmploye() {
        return employe;
    }
    
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date debut;
    
    public Date getDebut() {
        return debut;
    }
    
    public void setDebut(Date debut) {
        this.debut = debut;
    }
    
    @Column(nullable = false)
    private String description;
    
    public String getDescription() {
        return description;
    }
    
    @Column(nullable = false)
    private char status;
    
    public char getStatus() {
        return status;
    }
    
    public void setStatus(char status) {
        this.status = status;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;
    
    public Date getFin() {
        return fin;
    }
    
    public void setFin(Date fin) {
        this.fin = fin;
    }
    
    private String commentaire;
    
    public String getCommentaire() {
        return commentaire;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
