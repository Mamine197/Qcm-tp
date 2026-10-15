package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Démonstration de l'injection de dépendances via fichier de configuration XML.
 */
public class PresentationXML {

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("     TP 2 : CONTENEUR SPRING IOC AVEC FICHIER XML (applicationContext)");
        System.out.println("====================================================================");

        // Chargement du contexte Spring à partir du fichier XML
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Récupération du bean metier
        IMetier metier = (IMetier) context.getBean("metier");

        // Exécution et affichage du résultat
        double resultat = metier.calcul();
        System.out.println("Résultat (XML) = " + resultat);
    }
}
