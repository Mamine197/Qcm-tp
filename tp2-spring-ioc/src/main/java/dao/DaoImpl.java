package dao;

import org.springframework.stereotype.Component;

/**
 * Première implémentation de l'interface IDao.
 * Déclarée comme composant Spring avec l'identifiant "dao".
 */
@Component("dao")
public class DaoImpl implements IDao {

    @Override
    public double getValue() {
        System.out.println("[DaoImpl] Récupération de la valeur depuis DaoImpl (source 1)...");
        return 100.0;
    }
}
