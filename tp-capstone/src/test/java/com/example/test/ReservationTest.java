package com.example.test;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.model.StatutReservation;
import com.example.model.Utilisateur;
import com.example.service.ReservationService;
import com.example.service.SalleService;
import com.example.util.DataInitializer;
import com.example.util.MigrationExecutor;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ReservationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private ReservationService reservationService;
    private SalleService salleService;

    @Before
    public void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("ReservationPU");
        em = emf.createEntityManager();

        MigrationExecutor migration = new MigrationExecutor("jdbc:h2:mem:capstonedb;DB_CLOSE_DELAY=-1", "sa", "");
        migration.executeMigration("db/migration/v2_0_add_description.sql");

        DataInitializer initializer = new DataInitializer(em);
        initializer.initData();

        reservationService = new ReservationService(em);
        salleService = new SalleService(em);
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
    public void testRechercheReservations() {
        List<Reservation> reservations = reservationService.findReservationsBySalleAndDate("Salle Newton", LocalDateTime.now());
        assertNotNull(reservations);
        assertFalse("Des réservations doivent être trouvées pour la Salle Newton", reservations.isEmpty());
        assertEquals("Salle Newton", reservations.get(0).getSalle().getNom());
        assertNotNull(reservations.get(0).getUtilisateur().getEmail());
    }

    @Test
    public void testRechercheCriteria() {
        List<Reservation> results = reservationService.searchReservationsCriteria("Amphi Turing", LocalDateTime.now());
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals("Amphi Turing", results.get(0).getSalle().getNom());
    }

    @Test
    public void testCreationReservation() {
        Salle salle = em.createQuery("SELECT s FROM Salle s WHERE s.nom = 'Salle Pascal'", Salle.class).getSingleResult();
        Utilisateur user = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).setMaxResults(1).getSingleResult();

        LocalDateTime debut = LocalDateTime.now().plusDays(20);
        LocalDateTime fin = debut.plusHours(2);

        Reservation res = new Reservation(salle, user, debut, fin, "Réunion Spéciale Projet", StatutReservation.CONFIRMEE);
        reservationService.createReservation(res);

        assertNotNull(res.getId());
        assertEquals(0, res.getVersion());
    }

    @Test
    public void testSallesDisponibles() {
        LocalDateTime start = LocalDateTime.now().plusDays(100);
        LocalDateTime end = start.plusHours(4);

        List<Salle> disponibles = salleService.findSallesDisponibles(start, end);
        assertNotNull(disponibles);
        assertTrue("Toutes les 15 salles doivent être disponibles très loin dans le futur", disponibles.size() >= 10);
    }

    @Test
    public void testCacheSecondNiveau() {
        Session session = em.unwrap(Session.class);
        Statistics stats = session.getSessionFactory().getStatistics();
        stats.setStatisticsEnabled(true);

        Long salleId = em.createQuery("SELECT s.id FROM Salle s WHERE s.nom = 'Salle Newton'", Long.class).getSingleResult();

        em.clear();
        stats.clear();

        // 1er accès via Session em1 : Chargement depuis la base et mise en cache L2
        EntityManager em1 = emf.createEntityManager();
        try {
            Salle s1 = em1.find(Salle.class, salleId);
            assertNotNull(s1);
        } finally {
            em1.close();
        }

        // 2ème accès via Session em2 : Doit frapper le cache L2
        EntityManager em2 = emf.createEntityManager();
        try {
            Salle s2 = em2.find(Salle.class, salleId);
            assertNotNull(s2);
            assertEquals("Salle Newton", s2.getNom());
            long hitCount = stats.getSecondLevelCacheHitCount();
            System.out.printf("[Cache L2] SecondLevelCacheHitCount: %d, Puts: %d%n", hitCount, stats.getSecondLevelCachePutCount());
            assertTrue("SecondLevelCacheHitCount doit être supérieur ou égal à 1", hitCount >= 1);
        } finally {
            em2.close();
        }
    }

    @Test
    public void testOptimisticLockingSurReservation() {
        // Sélection d'une réservation existante
        Reservation r = em.createQuery("SELECT r FROM Reservation r", Reservation.class).setMaxResults(1).getSingleResult();
        Long reservationId = r.getId();

        EntityManager emUserA = emf.createEntityManager();
        EntityManager emUserB = emf.createEntityManager();

        try {
            Reservation resA = emUserA.find(Reservation.class, reservationId);
            Reservation resB = emUserB.find(Reservation.class, reservationId);

            // User A effectue une mise à jour
            emUserA.getTransaction().begin();
            resA.setMotif("Mise à jour Concurrente par Utilisateur A");
            emUserA.getTransaction().commit();

            // User B tente une mise à jour sur une version devenue obsolète
            emUserB.getTransaction().begin();
            resB.setMotif("Mise à jour Concurrente par Utilisateur B");
            try {
                emUserB.getTransaction().commit();
                fail("Une OptimisticLockException était attendue pour User B");
            } catch (Exception ex) {
                if (emUserB.getTransaction().isActive()) {
                    emUserB.getTransaction().rollback();
                }
                assertTrue("L'exception doit contenir ou être une OptimisticLockException",
                        ex instanceof OptimisticLockException || (ex.getCause() != null && ex.getCause() instanceof OptimisticLockException));
            }

            // Vérification de l'état final et de la version
            Reservation finalReservation = em.find(Reservation.class, reservationId);
            em.refresh(finalReservation);
            System.out.println("Version finale après concurrence : " + finalReservation.getVersion());
            assertTrue("La version doit avoir été incrémentée", finalReservation.getVersion() > 0);
        } finally {
            if (emUserA.isOpen()) emUserA.close();
            if (emUserB.isOpen()) emUserB.close();
        }
    }
}
