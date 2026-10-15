package com.example.util;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.model.StatutReservation;
import com.example.model.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service d'initialisation d'un jeu de données de test réaliste :
 * 20 utilisateurs, 15 salles de réunion/amphis, 100 réservations.
 */
public class DataInitializer {

    private static final Logger LOGGER = Logger.getLogger(DataInitializer.class.getName());
    private final EntityManager em;

    public DataInitializer(EntityManager em) {
        this.em = em;
    }

    public void initData() {
        Long existingSalles = em.createQuery("SELECT COUNT(s) FROM Salle s", Long.class).getSingleResult();
        if (existingSalles > 0) {
            LOGGER.info("Les données sont déjà initialisées dans la base.");
            return;
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Initialisation de 15 Salles
            String[] nomsSalles = {
                    "Salle Newton", "Salle Pascal", "Amphi Turing", "Salle Gauss", "Salle Lovelace",
                    "Salle Euler", "Salle Leibniz", "Amphi Dijkstra", "Salle Boole", "Salle Fermat",
                    "Salle Shannon", "Salle Hopper", "Salle Knuth", "Amphi Von Neumann", "Salle Berners-Lee"
            };
            int[] capacites = {30, 15, 120, 25, 20, 35, 18, 150, 22, 28, 40, 16, 24, 200, 32};
            int[] etages = {1, 1, 0, 2, 2, 3, 3, 0, 1, 2, 2, 3, 1, 0, 2};

            List<Salle> salles = new ArrayList<>();
            for (int i = 0; i < nomsSalles.length; i++) {
                Salle s = new Salle();
                s.setNom(nomsSalles[i]);
                s.setCapacite(capacites[i]);
                s.setEtage(etages[i]);
                s.setDescription("Salle équipée - Projecteur, Wi-Fi 6 et tableau blanc interactif");
                s.setNumero(nomsSalles[i].startsWith("Salle ") ? nomsSalles[i].substring(6) : nomsSalles[i]);
                em.persist(s);
                salles.add(s);
            }

            // 2. Initialisation de 20 Utilisateurs
            String[][] usersData = {
                    {"Mohamed Amine Nabil", "mohamedamine.nabil@emsi-edu.ma", "Informatique", "+212 601 234 567"},
                    {"Fatima Zahra Alami", "fatima.alami@emsi-edu.ma", "Génie Civil", "+212 602 345 678"},
                    {"Youssef Benjelloun", "youssef.benjelloun@emsi-edu.ma", "Informatique", "+212 603 456 789"},
                    {"Salma Berrada", "salma.berrada@emsi-edu.ma", "Finance", "+212 604 567 890"},
                    {"Karim El Amrani", "karim.amrani@emsi-edu.ma", "Réseaux & Télécoms", "+212 605 678 901"},
                    {"Houda Mansouri", "houda.mansouri@emsi-edu.ma", "Informatique", "+212 606 789 012"},
                    {"Omar Tazi", "omar.tazi@emsi-edu.ma", "Management", "+212 607 890 123"},
                    {"Khadija Chraibi", "khadija.chraibi@emsi-edu.ma", "Génie Industriel", "+212 608 901 234"},
                    {"Mehdi Kabbaj", "mehdi.kabbaj@emsi-edu.ma", "Automatisme", "+212 609 012 345"},
                    {"Zineb Guessous", "zineb.guessous@emsi-edu.ma", "Informatique", "+212 610 123 456"},
                    {"Anas Senhaji", "anas.senhaji@emsi-edu.ma", "Finance", "+212 611 234 567"},
                    {"Leila Filali", "leila.filali@emsi-edu.ma", "Ressources Humaines", "+212 612 345 678"},
                    {"Hamza Lahlou", "hamza.lahlou@emsi-edu.ma", "Informatique", "+212 613 456 789"},
                    {"Imane Sqalli", "imane.sqalli@emsi-edu.ma", "Génie Civil", "+212 614 567 890"},
                    {"Reda Bennani", "reda.bennani@emsi-edu.ma", "Informatique", "+212 615 678 901"},
                    {"Soukaina Tahiri", "soukaina.tahiri@emsi-edu.ma", "Réseaux & Télécoms", "+212 616 789 012"},
                    {"Tariq Fassi", "tariq.fassi@emsi-edu.ma", "Management", "+212 617 890 123"},
                    {"Meriem Bouzid", "meriem.bouzid@emsi-edu.ma", "Génie Industriel", "+212 618 901 234"},
                    {"Hassan Ouazzani", "hassan.ouazzani@emsi-edu.ma", "Finance", "+212 619 012 345"},
                    {"Nadia Semlali", "nadia.semlali@emsi-edu.ma", "Informatique", "+212 620 123 456"}
            };

            List<Utilisateur> utilisateurs = new ArrayList<>();
            for (String[] ud : usersData) {
                Utilisateur u = new Utilisateur(ud[0], ud[1], ud[2], ud[3]);
                em.persist(u);
                utilisateurs.add(u);
            }

            // 3. Initialisation de 100 Réservations réparties
            LocalDateTime baseTime = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0).withNano(0);
            String[] motifs = {
                    "Soutenance de PFE", "Cours Magistral Algorithmique", "Réunion Comité Pédagogique",
                    "Séminaire Intelligence Artificielle", "Atelier Cloud Computing", "Session Examen Partiel",
                    "Conférence Cybersécurité", "Hackathon EMSI Coding Challenge"
            };

            StatutReservation[] statuts = StatutReservation.values();

            for (int i = 0; i < 100; i++) {
                Salle salle = salles.get(i % salles.size());
                Utilisateur user = utilisateurs.get(i % utilisateurs.size());

                int dayOffset = (i / 10) + 1;
                int hourOffset = (i % 5) * 2;
                LocalDateTime debut = baseTime.plusDays(dayOffset).plusHours(hourOffset);
                LocalDateTime fin = debut.plusHours(2);

                Reservation res = new Reservation(
                        salle,
                        user,
                        debut,
                        fin,
                        motifs[i % motifs.length],
                        statuts[i % statuts.length]
                );
                em.persist(res);
            }

            tx.commit();
            LOGGER.info("Initialisation réussie : 15 salles, 20 utilisateurs, 100 réservations créées.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            LOGGER.severe("Erreur lors de l'initialisation des données : " + e.getMessage());
            throw e;
        }
    }
}
