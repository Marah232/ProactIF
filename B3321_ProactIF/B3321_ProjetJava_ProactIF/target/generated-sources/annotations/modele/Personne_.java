package modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modele.Adresse;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-21T21:58:03")
@StaticMetamodel(Personne.class)
public abstract class Personne_ { 

    public static volatile SingularAttribute<Personne, String> motDePasse;
    public static volatile SingularAttribute<Personne, Date> dateNaissance;
    public static volatile SingularAttribute<Personne, Adresse> adresse;
    public static volatile SingularAttribute<Personne, String> telephone;
    public static volatile SingularAttribute<Personne, Long> id;
    public static volatile SingularAttribute<Personne, String> nom;
    public static volatile SingularAttribute<Personne, String> prenom;
    public static volatile SingularAttribute<Personne, String> email;
    public static volatile SingularAttribute<Personne, Character> civilite;

}