package dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Implémentation alternative simulant un capteur IoT pour environnement de test.
 */
@Component("daoSensor")
@Profile("test")
public class DaoImplSensor implements IDao {

    @Override
    public double getValue() {
        System.out.println("[DaoImplSensor] Lecture en direct d'un capteur télémétrique...");
        return 250.0;
    }
}
