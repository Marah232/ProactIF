package modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-21T16:52:42")
@StaticMetamodel(Adresse.class)
public class Adresse_ { 

    public static volatile SingularAttribute<Adresse, String> ville;
    public static volatile SingularAttribute<Adresse, String> rue;
    public static volatile SingularAttribute<Adresse, Double> latitude;
    public static volatile SingularAttribute<Adresse, Integer> codePostal;
    public static volatile SingularAttribute<Adresse, String> complement;
    public static volatile SingularAttribute<Adresse, Double> longitude;

}