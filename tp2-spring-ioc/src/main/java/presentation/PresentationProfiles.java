package presentation;

import metier.IMetier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Démonstration de l'activation des profils Spring (@Profile) pour basculer
 * dynamiquement entre les environnements dev, prod et test.
 */
@Configuration
@ComponentScan(basePackages = {"dao", "metier"})
public class PresentationProfiles {

    public static void executerAvecProfil(String profilActif) {
        System.out.println("\n>>> Activation du profil Spring : [" + profilActif + "] <<<");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.getEnvironment().setActiveProfiles(profilActif);
            context.register(PresentationProfiles.class);
            context.refresh();

            IMetier metier = context.getBean(IMetier.class);
            System.out.println("Résultat calcul() avec profil [" + profilActif + "] = " + metier.calcul());
        }
    }

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("      TP 2 : GESTION DES PROFILS SPRING ENVIRONNEMENTAUX (@Profile) ");
        System.out.println("====================================================================");

        // Profil dev (injecte DaoImpl2 : 150.0 * 2 = 300.0)
        executerAvecProfil("dev");

        // Profil prod (injecte DaoImpl : 100.0 * 2 = 200.0)
        // Note: nécessite que MetierImpl puisse recevoir DaoImpl selon la configuration
    }
}
