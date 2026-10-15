package com.example;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.repository.SalleRepository;
import com.example.repository.SalleRepositoryImpl;
import com.example.service.SalleService;
import com.example.service.SalleServiceImpl;
import com.example.util.PaginationResult;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("=== TP 5 : Requêtes avancées, Criteria API et Pagination ===");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SallePU");
        EntityManager em = emf.createEntityManager();

        SalleRepository repository = new SalleRepositoryImpl(em);
        SalleService service = new SalleServiceImpl(repository);

        try {
            // Initialisation de salles
            Salle s1 = new Salle("Salle Conférence A", 50);
            Salle s2 = new Salle("Salle Réunion B", 20);
            Salle s3 = new Salle("Salle Formation C", 30);
            Salle s4 = new Salle("Salle Conseil D", 15);
            service.creerSalle(s1);
            service.creerSalle(s2);
            service.creerSalle(s3);
            service.creerSalle(s4);

            LocalDateTime now = LocalDateTime.now();
            Reservation r1 = new Reservation(s1, now.plusHours(1), now.plusHours(3), "Client EMSI");
            service.creerReservation(r1);

            System.out.println("\n--- Salles disponibles entre H+1 et H+2 ---");
            List<Salle> dispo = service.getAvailableRooms(now.plusHours(1), now.plusHours(2));
            dispo.forEach(System.out::println);

            System.out.println("\n--- Recherche Multi-critères avec Pagination (page 1, taille 2, minCapacite=20) ---");
            PaginationResult<Salle> page1 = service.searchRoomsWithPagination(null, 20, null, null, 1, 2);
            System.out.println(page1);
            page1.getItems().forEach(System.out::println);
        } finally {
            em.close();
            emf.close();
        }
    }
}