package com.example;

import com.example.model.Reservation;
import com.example.service.ReservationService;

import javax.persistence.OptimisticLockException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Gestionnaire de résilience avec mécanisme de Retry exponentiel
 * pour gérer les conflits de concurrence optimiste (@Version / OptimisticLockException).
 */
public class ReservationRetryHandler {

    private final int maxRetries;
    private final long backoffMillis;
    private final ReservationService service;
    private final AtomicInteger totalRetriesPerformed = new AtomicInteger(0);
    private final AtomicInteger successfulRetries = new AtomicInteger(0);

    public ReservationRetryHandler(ReservationService service) {
        this(service, 3, 50);
    }

    public ReservationRetryHandler(ReservationService service, int maxRetries, long backoffMillis) {
        this.service = service;
        this.maxRetries = maxRetries;
        this.backoffMillis = backoffMillis;
    }

    /**
     * Tente de modifier une réservation. En cas de conflit (OptimisticLockException),
     * recharge la dernière version de l'entité et réapplique la modification.
     */
    public Reservation modifierAvecRetry(Long id, Consumer<Reservation> modif) {
        int tentatives = 0;
        while (tentatives < maxRetries) {
            try {
                Reservation res = service.trouverParId(id);
                if (res == null) {
                    throw new IllegalArgumentException("Réservation avec ID " + id + " introuvable.");
                }
                modif.accept(res);
                Reservation updated = service.modifierReservation(res);
                if (tentatives > 0) {
                    successfulRetries.incrementAndGet();
                    System.out.println("[RetryHandler] Succès de la modification après " + tentatives + " retry(s).");
                }
                return updated;
            } catch (OptimisticLockException ole) {
                tentatives++;
                totalRetriesPerformed.incrementAndGet();
                System.out.printf("[RetryHandler] Tentative %d/%d : Conflit de verrouillage optimiste détecté sur l'ID %d. Réessai...%n",
                        tentatives, maxRetries, id);

                if (tentatives >= maxRetries) {
                    System.err.printf("[RetryHandler] Nombre maximum de tentatives (%d) atteint pour l'ID %d.%n", maxRetries, id);
                    throw ole;
                }

                if (backoffMillis > 0) {
                    try {
                        Thread.sleep(backoffMillis * tentatives);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interruption pendant l'attente de retry", ie);
                    }
                }
            }
        }
        return null;
    }

    public int getTotalRetriesPerformed() {
        return totalRetriesPerformed.get();
    }

    public int getSuccessfulRetries() {
        return successfulRetries.get();
    }

    public int getMaxRetries() {
        return maxRetries;
    }
}