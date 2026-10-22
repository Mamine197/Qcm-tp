package com.example;

import com.example.model.Reservation;
import com.example.model.StatutReservation;
import com.example.service.ReservationService;
import com.example.service.ReservationServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class OptimisticLockingTest {

    private ReservationService service;

    @Before
    public void setUp() {
        service = new ReservationServiceImpl();
    }

    @After
    public void tearDown() {
        service.fermer();
    }

    @Test(expected = OptimisticLockException.class)
    public void testOptimisticLockException() {
        Reservation r = new Reservation("Salle Test Lock", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        service.creerReservation(r);

        Reservation r1 = service.trouverParId(r.getId());
        Reservation r2 = service.trouverParId(r.getId());

        r1.setSalle("Salle Modif 1");
        service.modifierReservation(r1);

        r2.setSalle("Salle Modif 2");
        service.modifierReservation(r2); // Doit lever OptimisticLockException
    }

    @Test
    public void testVersionIncrementation() {
        Reservation r = new Reservation("Salle Innovation", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        service.creerReservation(r);
        assertEquals(Long.valueOf(0), r.getVersion());

        r.setMotif("Revue Sprint 1");
        Reservation v1 = service.modifierReservation(r);
        assertEquals(Long.valueOf(1), v1.getVersion());

        v1.setMotif("Revue Sprint 2");
        Reservation v2 = service.modifierReservation(v1);
        assertEquals(Long.valueOf(2), v2.getVersion());
    }

    @Test
    public void testRetryHandler() {
        Reservation r = new Reservation("Salle Test Retry", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        service.creerReservation(r);

        ReservationRetryHandler handler = new ReservationRetryHandler(service);
        Reservation updated = handler.modifierAvecRetry(r.getId(), res -> res.setSalle("Salle Retry OK"));

        assertNotNull(updated);
        assertEquals("Salle Retry OK", updated.getSalle());
    }

    @Test
    public void testRechercheEtAnnulation() {
        Reservation r = new Reservation("Salle Atlas VIP", LocalDateTime.now(), LocalDateTime.now().plusHours(3), "Comité Direction", "Direction");
        service.creerReservation(r);

        List<Reservation> trouvees = service.rechercherParSalle("Atlas");
        assertFalse(trouvees.isEmpty());
        assertEquals("Salle Atlas VIP", trouvees.get(0).getSalle());

        Reservation annulee = service.annulerReservation(r.getId());
        assertEquals(StatutReservation.ANNULEE, annulee.getStatut());
    }

    @Test
    public void testConcurrentThreadsSimulation() throws InterruptedException {
        Reservation r = new Reservation("Salle Multithread", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        service.creerReservation(r);
        Long resId = r.getId();

        // Les deux threads chargent la même version initiale (version = 0)
        Reservation entity1 = service.trouverParId(resId);
        Reservation entity2 = service.trouverParId(resId);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        AtomicInteger conflictCount = new AtomicInteger(0);
        AtomicInteger successCount = new AtomicInteger(0);

        executor.submit(() -> {
            try {
                entity1.setSalle("Salle Thread 1");
                service.modifierReservation(entity1);
                successCount.incrementAndGet();
            } finally {
                latch1.countDown();
            }
        });

        executor.submit(() -> {
            try {
                latch1.await(); // Attendre que Thread 1 ait commité (la version en BD passe à 1)
                entity2.setSalle("Salle Thread 2");
                service.modifierReservation(entity2); // entity2 a toujours version 0 -> conflit
                successCount.incrementAndGet();
            } catch (OptimisticLockException ole) {
                conflictCount.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch2.countDown();
            }
        });

        assertTrue(latch2.await(5, TimeUnit.SECONDS));
        executor.shutdown();

        assertEquals(1, successCount.get());
        assertEquals(1, conflictCount.get());
    }
}