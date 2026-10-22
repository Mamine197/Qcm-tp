package com.example;

import com.example.model.Auteur;
import com.example.service.DataInitService;
import com.example.service.PerformanceTestService;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Application principale de démonstration :
 * - Initialisation du catalogue littéraire
 * - Benchmark comparatif N+1 vs JOIN FETCH vs Entity Graph
 * - Démonstration du Cache de second niveau L2 avec statistiques Hibernate
 */
public class App {

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println("  TP 7 : Cache de Second Niveau, JOIN FETCH et Entity Graph (JPA/Hibernate)  ");
        System.out.println("==========================================================================");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-cache-pu");
        EntityManager em = emf.createEntityManager();

        try {
            // 1. Initialisation des données
            System.out.println("\n[1] Initialisation du catalogue avec 5 auteurs et leurs livres...");
            DataInitService initService = new DataInitService(em);
            initService.initData();

            // 2. Mesure sans optimisation (Problème N+1)
            em.clear();
            PerformanceTestService perfService = new PerformanceTestService(em);
            perfService.testPerformanceSansCache();

            // 3. Mesure avec JOIN FETCH
            em.clear();
            perfService.testPerformanceAvecJoinFetch();

            // 4. Mesure avec Entity Graph
            em.clear();
            perfService.testPerformanceAvecEntityGraph();

            // 5. Test du Cache de second niveau L2
            System.out.println("\n[2] Démonstration du Cache L2 (EhCache) inter-sessions :");
            Session session = em.unwrap(Session.class);
            Statistics stats = session.getSessionFactory().getStatistics();
            stats.setStatisticsEnabled(true);
            stats.clear();

            Long idAuteur = em.createQuery("SELECT a.id FROM Auteur a", Long.class)
                    .setMaxResults(1)
                    .getSingleResult();

            em.clear();

            // Premier accès via Session 1 -> L2 Miss & Put
            EntityManager em1 = emf.createEntityManager();
            try {
                System.out.println("-> Session 1 : Chargement Auteur ID " + idAuteur + " (Accès Base)");
                Auteur a1 = em1.find(Auteur.class, idAuteur);
                System.out.println("   Trouvé : " + a1.getNom() + ", Hits L2 = " + stats.getSecondLevelCacheHitCount() + ", Puts L2 = " + stats.getSecondLevelCachePutCount());
            } finally {
                em1.close();
            }

            // Deuxième accès via Session 2 -> L2 Hit (0 requête SQL)
            EntityManager em2 = emf.createEntityManager();
            try {
                System.out.println("-> Session 2 : Rechargement Auteur ID " + idAuteur + " (Accès Cache L2)");
                Auteur a2 = em2.find(Auteur.class, idAuteur);
                System.out.println("   Trouvé : " + a2.getNom() + ", Hits L2 = " + stats.getSecondLevelCacheHitCount() + ", Puts L2 = " + stats.getSecondLevelCachePutCount());
            } finally {
                em2.close();
            }

            System.out.println("\n=== Fin de la démonstration avec succès ===");

        } finally {
            if (em.isOpen()) {
                em.close();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
    }
}
