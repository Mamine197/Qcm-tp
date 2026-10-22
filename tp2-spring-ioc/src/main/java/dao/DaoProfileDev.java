package dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Implémentation spécifique au profil 'dev' (Exercice 3).
 */
@Component("daoProfileDev")
@Profile("dev")
public class DaoProfileDev implements IDao {

    @Override
    public double getValue() {
        System.out.println("[DaoProfileDev] Données de développement (Profil dev actif)");
        return 150.0;
    }
}
