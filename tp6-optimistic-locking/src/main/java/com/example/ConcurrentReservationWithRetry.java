package com.example;

import com.example.model.Reservation;
import com.example.service.ReservationService;
import com.example.service.ReservationServiceImpl;

import java.time.LocalDateTime;

public class ConcurrentReservationWithRetry {
    public static void main(String[] args) {
        System.out.println("=== Gestion des conflits avec mécanisme de Retry ===");
        ReservationService service = new ReservationServiceImpl();
        ReservationRetryHandler retryHandler = new ReservationRetryHandler(service);

        try {
            Reservation r = new Reservation("Salle Platine", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
            service.creerReservation(r);

            Reservation updated = retryHandler.modifierAvecRetry(r.getId(), res -> {
                res.setSalle("Salle Platine - Validée avec Retry");
            });

            System.out.println("Réservation mise à jour avec succès: " + updated);
        } finally {
            service.fermer();
        }
    }
}