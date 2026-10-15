package dao;

/**
 * Interface définissant le contrat pour l'accès aux données.
 * Permet un couplage faible entre la couche métier et les différentes implémentations.
 */
public interface IDao {
    double getValue();
}
