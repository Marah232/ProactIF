package modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modele.Intervention;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-18T12:47:39")
@StaticMetamodel(Employe.class)
public class Employe_ extends Personne_ {

    public static volatile SingularAttribute<Employe, Integer> heureFinTravail;
    public static volatile SingularAttribute<Employe, Integer> heureDebutTravail;
    public static volatile SingularAttribute<Employe, Intervention> interventionEnCours;
    public static volatile SingularAttribute<Employe, Integer> version;

}