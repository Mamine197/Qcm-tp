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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-inheritance");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // 1. Stratégie SINGLE_TABLE (Véhicules)
        Vehicule v1 = new Voiture("BMW Série 3", LocalDate.of(2022, 5, 12), 5, true, "Hybride");
        Vehicule m1 = new Moto("Ducati Monster", LocalDate.of(2023, 3, 20), 937, false);
        em.persist(v1);
        em.persist(m1);

        // 2. Stratégie JOINED (Employés)
        Employe dev1 = new Developpeur("Nabil", "Mohamed Amine", "amine.nabil@example.com", "Java / Spring Boot", "Cloud & Backend", 4);
        Employe mgr1 = new Manager("Benjelloun", "Fatima-Zahra", "fatima.benjelloun@example.com", "Direction Technique & IT", 6, 7500.0);
        em.persist(dev1);
        em.persist(mgr1);

        // 3. Stratégie TABLE_PER_CLASS (Produits)
        Produit livre1 = new Livre("Architecture Logicielle Moderne", 45.0, "Martin Fowler", "978-0134757599");
        Produit pc1 = new Electronique("MacBook Pro M3", 1850.0, 24, "Apple");
        em.persist(livre1);
        em.persist(pc1);

        em.getTransaction().commit();

        // Vider le contexte de persistance pour forcer la lecture depuis la base H2
        em.clear();

        System.out.println("=== 1. Résultats Requête Polymorphique SINGLE_TABLE ===");
        List<Vehicule> vehicules = em.createQuery("FROM Vehicule", Vehicule.class).getResultList();
        for (Vehicule v : vehicules) {
            System.out.println("- [" + v.getClass().getSimpleName() + "] Marque: " + v.getMarque() + ", Année: " + v.getAnneeFabrication());
        }

        System.out.println("\n=== 2. Résultats Requête Polymorphique JOINED ===");
        List<Employe> employes = em.createQuery("FROM Employe", Employe.class).getResultList();
        for (Employe e : employes) {
            System.out.println("- [" + e.getClass().getSimpleName() + "] Nom: " + e.getNom() + " " + e.getPrenom() + ", Email: " + e.getEmail());
        }

        System.out.println("\n=== 3. Résultats Requête Polymorphique TABLE_PER_CLASS ===");
        List<Produit> produits = em.createQuery("FROM Produit", Produit.class).getResultList();
        for (Produit p : produits) {
            System.out.println("- [" + p.getClass().getSimpleName() + "] Produit: " + p.getNom() + ", Prix: " + p.getPrix() + " EUR");
        }

        em.close();
        emf.close();
    }
}