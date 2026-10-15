package com.example;

import com.example.entities.*;
import com.example.service.CrudService;

import java.math.BigDecimal;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Révision : Gestion Commerciale avec Hibernate & JPA ===");

        CrudService<Categorie, Long> categorieService = new CrudService<>(Categorie.class);
        CrudService<Produit, Long> produitService = new CrudService<>(Produit.class);
        CrudService<Client, Long> clientService = new CrudService<>(Client.class);
        CrudService<Commande, Long> commandeService = new CrudService<>(Commande.class);

        // 1. Création des Catégories
        Categorie catInfo = new Categorie("Matériel Informatique Pro");
        Categorie catAccessoires = new Categorie("Accessoires & Réseau");
        categorieService.create(catInfo);
        categorieService.create(catAccessoires);
        System.out.println("Catégories créées : " + catInfo.getId() + ", " + catAccessoires.getId());

        // 2. Création des Produits
        Produit p1 = new Produit("Station de Travail Dell Precision", new BigDecimal("1899.99"), catInfo);
        Produit p2 = new Produit("Écran 4K UltraWide 34 pouces", new BigDecimal("649.50"), catInfo);
        Produit p3 = new Produit("Switch Gigabit 24 Ports PoE+", new BigDecimal("279.00"), catAccessoires);
        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        // 3. Création Client (Héritage JOINED)
        Client client = new Client("Mohamed Amine Nabil", "amine.nabil@emsi-edu.ma");
        clientService.create(client);
        System.out.println("Client enregistré avec ID : " + client.getId());

        // 4. Création Commande avec relation N:N
        Commande commande = new Commande(client);
        commande.addProduit(p1, 2);
        commande.addProduit(p3, 4);
        commandeService.create(commande);
        System.out.println("Commande #" + commande.getId() + " passée avec succès pour " + client.getNom());

        // 5. Recherche par critères
        System.out.println("\n--- Recherche de produits (prix >= 300 EUR) ---");
        List<Produit> chers = produitService.searchProduits(null, new BigDecimal("300.00"), null);
        for (Produit p : chers) {
            System.out.println("- " + p.getLibelle() + " : " + p.getPrix() + " EUR (Catégorie: " + p.getCategorie().getNom() + ")");
        }

        // 6. Pagination
        System.out.println("\n--- Pagination des produits (Page 1, taille 2) ---");
        List<Produit> page1 = produitService.findPage(1, 2);
        page1.forEach(p -> System.out.println("P1: " + p.getLibelle()));
    }
}
