package com.example;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.repository.SalleRepository;
import com.example.repository.SalleRepositoryImpl;
import com.example.service.SalleService;
import com.example.service.SalleServiceImpl;
import com.example.util.PaginationResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class AppTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private SalleService service;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("SallePU");
        em = emf.createEntityManager();
        SalleRepository repository = new SalleRepositoryImpl(em);
        service = new SalleServiceImpl(repository);
    }

    @After
    public void tearDown() {
        if (em.isOpen()) em.close();
        if (emf.isOpen()) emf.close();
    }

    @Test
    public void testAvailableRoomsAndPagination() {
        Salle s1 = new Salle("Salle Bleue", 10);
        Salle s2 = new Salle("Salle Verte", 25);
        Salle s3 = new Salle("Salle Rouge", 40);
        service.creerSalle(s1);
        service.creerSalle(s2);
        service.creerSalle(s3);

        LocalDateTime start = LocalDateTime.of(2026, 10, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 15, 12, 0);

        Reservation r = new Reservation(s2, start, end, "Entreprise X");
        service.creerReservation(r);

        // Salles dispo sur ce créneau (s1 et s3, pas s2)
        List<Salle> dispo = service.getAvailableRooms(start.plusMinutes(30), start.plusMinutes(60));
        assertEquals(2, dispo.size());

        // Pagination
        PaginationResult<Salle> p = service.searchRoomsWithPagination(null, 15, null, null, 1, 1);
        assertEquals(1, p.getItems().size());
        assertEquals(2, p.getTotalItems());
        assertEquals(2, p.getTotalPages());
    }
}