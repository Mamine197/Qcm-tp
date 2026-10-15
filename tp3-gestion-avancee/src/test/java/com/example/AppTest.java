package com.example;

import com.example.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class AppTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("gestion-reservations-pu");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        if (em.isOpen()) em.close();
        if (emf.isOpen()) emf.close();
    }

    @Test
    public void testPersistAndRelations() {
        em.getTransaction().begin();

        Utilisateur user = new Utilisateur("Bob Martin", "bob@example.com");
        Salle salle = new Salle("Salle Conférence");
        Equipement eq = new Equipement("Vidéoprojecteur HD");

        salle.addEquipement(eq);
        em.persist(salle);

        Reservation res = new Reservation(user, salle);
        user.addReservation(res);
        em.persist(user);

        em.getTransaction().commit();

        // Vérification
        Utilisateur savedUser = em.find(Utilisateur.class, user.getId());
        assertNotNull(savedUser);
        assertEquals(1, savedUser.getReservations().size());

        Salle savedSalle = em.find(Salle.class, salle.getId());
        assertEquals(1, savedSalle.getEquipements().size());
    }

    @Test
    public void testOrphanRemoval() {
        em.getTransaction().begin();
        Utilisateur user = new Utilisateur("Charlie", "charlie@example.com");
        Salle salle = new Salle("Salle B");
        em.persist(salle);

        Reservation res = new Reservation(user, salle);
        user.addReservation(res);
        em.persist(user);
        em.getTransaction().commit();

        Long resId = res.getId();

        em.getTransaction().begin();
        Utilisateur u = em.find(Utilisateur.class, user.getId());
        u.getReservations().clear();
        em.getTransaction().commit();

        Reservation deletedRes = em.find(Reservation.class, resId);
        assertNull("Orphan removal should delete unreferenced reservation", deletedRes);
    }
}