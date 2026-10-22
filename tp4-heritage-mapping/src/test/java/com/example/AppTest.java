package com.example;

import com.example.model.joined.Developpeur;
import com.example.model.joined.Employe;
import com.example.model.joined.Manager;
import com.example.model.singletable.Moto;
import com.example.model.singletable.Vehicule;
import com.example.model.singletable.Voiture;
import com.example.model.tableperclass.Electronique;
import com.example.model.tableperclass.Livre;
import com.example.model.tableperclass.Produit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class AppTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("hibernate-inheritance");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }

    @Test
    public void testInheritanceStrategies() {
        em.getTransaction().begin();

        // 1. Single Table
        Vehicule v1 = new Voiture("BMW Série 3", LocalDate.of(2022, 5, 12), 5, true, "Hybride");
        Vehicule m1 = new Moto("Ducati Monster", LocalDate.of(2023, 3, 20), 937, false);
        em.persist(v1);
        em.persist(m1);

        // 2. Joined Table
        Employe dev1 = new Developpeur("Nabil", "Mohamed Amine", "amine.nabil@example.com", "Java / Spring Boot", "Cloud & Backend", 4);
        Employe mgr1 = new Manager("Benjelloun", "Fatima-Zahra", "fatima.benjelloun@example.com", "Direction Technique & IT", 6, 7500.0);
        em.persist(dev1);
        em.persist(mgr1);

        // 3. Table Per Class
        Produit livre1 = new Livre("Architecture Logicielle Moderne", 45.0, "Martin Fowler", "978-0134757599");
        Produit pc1 = new Electronique("MacBook Pro M3", 1850.0, 24, "Apple");
        em.persist(livre1);
        em.persist(pc1);

        em.getTransaction().commit();

        em.clear();

        // Vérifications SINGLE_TABLE
        List<Vehicule> vehicules = em.createQuery("FROM Vehicule", Vehicule.class).getResultList();
        assertEquals(2, vehicules.size());
        List<Moto> motos = em.createQuery("SELECT m FROM Moto m", Moto.class).getResultList();
        assertEquals(1, motos.size());
        assertEquals("Ducati Monster", motos.get(0).getMarque());

        // Vérifications JOINED
        List<Employe> employes = em.createQuery("FROM Employe", Employe.class).getResultList();
        assertEquals(2, employes.size());
        List<Manager> managers = em.createQuery("SELECT m FROM Manager m", Manager.class).getResultList();
        assertEquals(1, managers.size());
        assertEquals("Benjelloun", managers.get(0).getNom());

        // Vérifications TABLE_PER_CLASS
        List<Produit> produits = em.createQuery("FROM Produit", Produit.class).getResultList();
        assertEquals(2, produits.size());
        List<Livre> livres = em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
        assertEquals(1, livres.size());
        assertNotNull(livres.get(0).getDateCreation());
    }
}