package modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modele.Client;
import modele.Employe;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-21T16:52:42")
@StaticMetamodel(Intervention.class)
public abstract class Intervention_ { 

    public static volatile SingularAttribute<Intervention, Date> debut;
    public static volatile SingularAttribute<Intervention, Employe> employe;
    public static volatile SingularAttribute<Intervention, Client> client;
    public static volatile SingularAttribute<Intervention, String> description;
    public static volatile SingularAttribute<Intervention, Date> fin;
    public static volatile SingularAttribute<Intervention, Long> id;
    public static volatile SingularAttribute<Intervention, String> commentaire;
    public static volatile SingularAttribute<Intervention, Character> status;

}