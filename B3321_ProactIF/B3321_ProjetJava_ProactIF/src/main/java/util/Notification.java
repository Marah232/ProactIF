package util;

public class Notification {
    
    public static void envoyerMail(
            String expediteur,
            String destinataire,
            String sujet,
            String corps) {
        
        System.out.println("----------- MAIL -----------");
        System.out.println("Expediteur : " + expediteur);
        System.out.println("Pour : " + destinataire);
        System.out.println("Sujet : " + sujet);
        System.out.println("Corps :\n" + corps);
        System.out.println("----------------------------");
    }
    
    public static void envoyerSMS(
            String expediteur,
            String destinataire,
            String message) {
        
        System.out.println("------------ SMS -----------");
        System.out.println("Expediteur : " + expediteur);
        System.out.println("Pour : " + destinataire);
        System.out.println("Message :\n" + message);
        System.out.println("----------------------------");
    }
}
