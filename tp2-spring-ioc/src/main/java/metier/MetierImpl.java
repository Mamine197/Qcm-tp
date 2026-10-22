package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Implémentation du service métier.
 * Démontre l'Inversion de Contrôle (IoC) et l'Injection de Dépendances (DI) via Spring.
 */
@Component("metier")
public class MetierImpl implements IMetier {

    @Autowired
    @Qualifier("dao2") // Spécifie quelle implémentation de IDao injecter par défaut
    private IDao dao;

    public MetierImpl() {
    }

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        if (dao == null) {
            throw new IllegalStateException("Dépendance IDao manquante : le bean n'a pas été injecté !");
        }
        double data = dao.getValue();
        return data * 2;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public IDao getDao() {
        return dao;
    }
}
