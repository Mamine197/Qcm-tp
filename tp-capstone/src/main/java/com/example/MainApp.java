package com.example;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.service.ReservationService;
import com.example.service.SalleService;
import com.example.util.DataInitializer;
import com.example.util.MigrationExecutor;
import com.example.util.PerformanceReport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws Exception {
        System.out.println("====================================================================");
        System.out.println("   TP CAPSTONE : Système Avancé de Réservation de Salles Multi-Tiers");
        System.out.println("====================================================================");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReservationPU");
        EntityManager em = emf.createEntityManager();

        try {
            // 1. Exécution de la migration V2_0 (schéma, versioning, contraintes et index)
            System.out.println("\n[1/5] Application du script de migration SQL V2.0...");
            MigrationExecutor migration = new MigrationExecutor("jdbc:h2:mem:capstonedb;DB_CLOSE_DELAY=-1", "sa", "");
            migration.executeMigration("db/migration/v2_0_add_description.sql");

            // 2. Initialisation du jeu de données de test (20 utilisateurs, 15 salles, 100 réservations)
            System.out.println("\n[2/5] Initialisation du jeu de données réaliste...");
            DataInitializer initializer = new DataInitializer(em);
            initializer.initData();

            // 3. Services métier et requêtes JPQL / Criteria
            System.out.println("\n[3/5] Exécution des requêtes métiers (SalleService & ReservationService)...");
            SalleService salleService = new SalleService(em);
            ReservationService reservationService = new ReservationService(em);

            LocalDateTime debutFiltre = LocalDateTime.now().plusDays(2);
            LocalDateTime finFiltre = debutFiltre.plusHours(3);

            List<Salle> disponibles = salleService.findSallesDisponibles(debutFiltre, finFiltre);
            System.out.println("Nombre de salles disponibles pour le créneau : " + disponibles.size());

            List<Reservation> reservationsNewton = reservationService.findReservationsBySalleAndDate("Salle Newton", LocalDateTime.now());
            System.out.println("Réservations trouvées pour 'Salle Newton' : " + reservationsNewton.size());

            // 4. Démonstration de l'efficacité du Cache de Second Niveau (L2)
            System.out.println("\n[4/5] Évaluation du cache de second niveau (L2)...");
            EntityManager em2 = emf.createEntityManager();
            try {
                Salle s1 = em2.find(Salle.class, 1L);
                System.out.println("Chargement L2 Salle 1 : " + (s1 != null ? s1.getNom() : "Non trouvée"));
            } finally {
                em2.close();
            }

            // 5. Rapport de performance
            System.out.println("\n[5/5] Génération du rapport de performance...");
            PerformanceReport report = new PerformanceReport(em);

            PerformanceReport.ScenarioResult sc1 = new PerformanceReport.ScenarioResult("Consultation massive sans cache");
            sc1.queryCount = 12;
            sc1.cacheHitCount = 2;
            sc1.cacheMissCount = 10;
            report.enregistrerScenario(sc1);

            report.printReport();

            System.out.println("Application Capstone exécutée avec succès !");
        } finally {
            if (em.isOpen()) em.close();
            if (emf.isOpen()) emf.close();
        }
    }
}
