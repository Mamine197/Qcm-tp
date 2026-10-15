package presentation;

import dao.DaoImpl;
import dao.IDao;
import metier.MetierImpl;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Démonstration des approches traditionnelles :
 * 1. Instanciation statique (couplage fort)
 * 2. Instanciation dynamique par réflexion (couplage faible sans framework)
 */
public class Presentation {

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("       TP 2 : DÉMONSTRATION DU COUPLAGE FORT VS COUPLAGE FAIBLE    ");
        System.out.println("====================================================================");

        // 1. Instanciation Statique (Couplage Fort - Mauvaise Pratique)
        System.out.println("\n[1] Instanciation Statique (Couplage fort avec new) :");
        DaoImpl daoStatique = new DaoImpl();
        MetierImpl metierStatique = new MetierImpl();
        metierStatique.setDao(daoStatique);
        System.out.println("Résultat (Statique) = " + metierStatique.calcul());

        // 2. Instanciation Dynamique par Réflexion (Couplage faible avec config)
        System.out.println("\n[2] Instanciation Dynamique par Réflexion Java :");
        try {
            String daoClassName = "dao.DaoImpl2";
            String metierClassName = "metier.MetierImpl";

            Class<?> cDao = Class.forName(daoClassName);
            IDao daoDynamique = (IDao) cDao.getDeclaredConstructor().newInstance();

            Class<?> cMetier = Class.forName(metierClassName);
            Object metierDynamique = cMetier.getDeclaredConstructor().newInstance();

            Method setDaoMethod = cMetier.getMethod("setDao", IDao.class);
            setDaoMethod.invoke(metierDynamique, daoDynamique);

            Method calculMethod = cMetier.getMethod("calcul");
            double resultat = (double) calculMethod.invoke(metierDynamique);

            System.out.println("Résultat (Réflexion avec " + daoClassName + ") = " + resultat);
        } catch (Exception e) {
            System.err.println("Erreur réflexion : " + e.getMessage());
        }
    }
}
