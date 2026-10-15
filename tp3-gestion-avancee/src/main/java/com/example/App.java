package com.example;

import com.example.model.*;
import javax.persistence.*;

public class App {
    public static void main(String[] args) {
        System.out.println("=== TP 3 : Gestion avancée des entités et relations ===");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-reservations-pu");
        EntityManager em = emf.createEntityManager();

        try {
            // 1. Création et association des entités
            em.getTransaction().begin();

            Utilisateur user = new Utilisateur();
            user.setEmail("alice@example.com");
            user.setNom("Alice Dupont");

            Salle salle = new Salle();
            salle.setNom("Salle A");

            Equipement projecteur = new Equipement();
            projecteur.setNom("Projecteur");

            salle.addEquipement(projecteur);
            em.persist(salle); // persist salle avec equipement en cascade

            Reservation reservation = new Reservation();
            reservation.setSalle(salle);
            reservation.setUtilisateur(user);

            user.addReservation(reservation);

            em.persist(user); // cascade persist propagée vers Reservation

            em.getTransaction().commit();
            System.out.println("Utilisateur, Salle, Equipement et Réservation enregistrés avec succès !");

            // 2. Test modification / orphanRemoval
            em.getTransaction().begin();
            Utilisateur foundUser = em.find(Utilisateur.class, user.getId());
            if (!foundUser.getReservations().isEmpty()) {
                Reservation res = foundUser.getReservations().iterator().next();
                foundUser.getReservations().remove(res);
                System.out.println("Réservation retirée de la collection (orphanRemoval)");
            }
            em.getTransaction().commit();

            System.out.println("Succès de l'exécution de l'application !");
        } finally {
            em.close();
            emf.close();
        }
    }
}