package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration et exécution de l'application via le conteneur Spring IoC basé sur les annotations.
 */
@Configuration
@ComponentScan(basePackages = {"dao", "metier"})
public class Presentation2 {

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("    TP 2 : CONTENEUR SPRING IOC AVEC ANNOTATIONS & @QUALIFIER       ");
        System.out.println("====================================================================");

        // 1. Initialisation du contexte Spring à partir des annotations de la classe de configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(Presentation2.class);

        // 2. Récupération du bean IMetier géré par le conteneur IoC
        IMetier metier = context.getBean(IMetier.class);

        // 3. Exécution de la méthode calcul()
        double resultat = metier.calcul();
        System.out.println("Résultat = " + resultat);
        System.out.println("Type réel de la classe métier injectée : " + metier.getClass().getSimpleName());
    }
}
