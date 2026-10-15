package dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Implémentation spécifique au profil 'prod' (Exercice 3).
 */
@Component("daoProfileProd")
@Profile("prod")
public class DaoProfileProd implements IDao {

    @Override
    public double getValue() {
        System.out.println("[DaoProfileProd] Données de production (Profil prod actif)");
        return 100.0;
    }
}
