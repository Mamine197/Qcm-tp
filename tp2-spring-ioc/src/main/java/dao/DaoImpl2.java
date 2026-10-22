package dao;

import org.springframework.stereotype.Component;

/**
 * Seconde implémentation de l'interface IDao.
 * Déclarée comme composant Spring avec l'identifiant "dao2".
 */
@Component("dao2")
public class DaoImpl2 implements IDao {

    @Override
    public double getValue() {
        System.out.println("[DaoImpl2] Récupération de la valeur depuis DaoImpl2 (source 2)...");
        return 150.0;
    }
}
