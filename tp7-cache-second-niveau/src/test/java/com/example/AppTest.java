package com.example;

import com.example.model.Auteur;
import com.example.model.Livre;
import com.example.service.DataInitService;
import com.example.service.PerformanceTestService;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Suite de tests unitaires validant :
 * 1. La résolution du problème N+1 via JOIN FETCH
 * 2. La résolution du problème N+1 via Entity Graph déclaratif
 * 3. L'efficacité du cache de second niveau (EhCache L2) sur les entités
 * 4. La mise en cache des collections associées
 */
public class AppTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("hibernate-cache-pu");
        em = emf.createEntityManager();

        // Initialisation du jeu d'essai
        DataInitService initService = new DataInitService(em);
        initService.initData();
        em.clear();
    }

    @After
    public void tearDown() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    public void testJoinFetchAndEntityGraph() {
        PerformanceTestService perf = new PerformanceTestService(em);

        // 1. Test avec JOIN FETCH
        List<Auteur> listFetch = perf.testPerformanceAvecJoinFetch();
        assertNotNull(listFetch);
        assertFalse(listFetch.isEmpty());
        assertTrue(listFetch.get(0).getNombreLivres() > 0);

        // 2. Test avec Entity Graph
        em.clear();
        List<Auteur> listGraph = perf.testPerformanceAvecEntityGraph();
        assertNotNull(listGraph);
        assertFalse(listGraph.isEmpty());
        assertEquals(listFetch.size(), listGraph.size());
    }

    @Test
    public void testSecondLevelCache() {
        Session session = em.unwrap(Session.class);
        Statistics stats = session.getSessionFactory().getStatistics();
        stats.setStatisticsEnabled(true);

        // Récupérer un ID d'auteur
        Auteur firstAuthor = em.createQuery("SELECT a FROM Auteur a", Auteur.class)
                .setMaxResults(1)
                .getSingleResult();
        Long auteurId = firstAuthor.getId();

        em.clear();
        stats.clear();

        // 1er accès via Session 1 -> Charge depuis la DB et enregistre dans le Cache L2
        EntityManager em1 = emf.createEntityManager();
        try {
            Auteur a1 = em1.find(Auteur.class, auteurId);
            assertNotNull(a1);
        } finally {
            em1.close();
        }

        // 2ème accès via Session 2 -> Doit provenir du Cache L2 sans toucher la DB
        EntityManager em2 = emf.createEntityManager();
        try {
            Auteur a2 = em2.find(Auteur.class, auteurId);
            assertNotNull(a2);
            assertEquals(auteurId, a2.getId());
        } finally {
            em2.close();
        }

        long hits = stats.getSecondLevelCacheHitCount();
        System.out.printf("[Cache L2 Test] Hits: %d, Puts: %d%n", hits, stats.getSecondLevelCachePutCount());
        assertTrue("Le cache L2 doit enregistrer au moins 1 hit", hits >= 1);
    }

    @Test
    public void testCollectionSecondLevelCache() {
        Session session = em.unwrap(Session.class);
        Statistics stats = session.getSessionFactory().getStatistics();
        stats.setStatisticsEnabled(true);

        Long auteurId = em.createQuery("SELECT a.id FROM Auteur a", Long.class)
                .setMaxResults(1)
                .getSingleResult();

        em.clear();
        stats.clear();

        // Session 1: Charge l'auteur et initialise la collection
        EntityManager em1 = emf.createEntityManager();
        try {
            Auteur a1 = em1.find(Auteur.class, auteurId);
            assertNotNull(a1);
            int size1 = a1.getLivres().size();
            assertTrue(size1 > 0);
        } finally {
            em1.close();
        }

        // Session 2: Charge la collection depuis le cache L2
        EntityManager em2 = emf.createEntityManager();
        try {
            Auteur a2 = em2.find(Auteur.class, auteurId);
            assertNotNull(a2);
            int size2 = a2.getLivres().size();
            assertTrue(size2 > 0);
        } finally {
            em2.close();
        }

        assertTrue("Le cache de collection doit avoir été sollicité", stats.getSecondLevelCacheHitCount() >= 1);
    }
}
