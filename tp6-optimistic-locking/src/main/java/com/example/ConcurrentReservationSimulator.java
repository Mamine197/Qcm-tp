package com.example;

import com.example.model.Reservation;
import com.example.service.ReservationService;
import com.example.service.ReservationServiceImpl;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;

public class ConcurrentReservationSimulator {
    public static void main(String[] args) {
        System.out.println("=== Simulation de conflit concurrent avec Optimistic Locking ===");
        ReservationService service = new ReservationServiceImpl();

        try {
            Reservation r = new Reservation("Salle Diamant", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
            service.creerReservation(r);

            // Utilisateur 1 et Utilisateur 2 chargent la même réservation
            Reservation user1Res = service.trouverParId(r.getId());
            Reservation user2Res = service.trouverParId(r.getId());

            // Utilisateur 1 modifie et commit
            user1Res.setSalle("Salle Diamant - Modifiée par User 1");
            service.modifierReservation(user1Res);
            System.out.println("Transaction 1 : modification réussie.");

            // Utilisateur 2 essaie de modifier avec sa version obsolète
            try {
                user2Res.setSalle("Salle Diamant - Modifiée par User 2");
                service.modifierReservation(user2Res);
                System.out.println("Transaction 2 : modification réussie (inattendu)");
            } catch (OptimisticLockException ole) {
                System.out.println("Transaction 2 : conflit détecté - " + ole.getMessage());
            }
        } finally {
            service.fermer();
        }
    }
}