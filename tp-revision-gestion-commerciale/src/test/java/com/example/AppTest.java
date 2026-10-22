package com.example;

import com.example.entities.*;
import com.example.service.CrudService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class AppTest {

    private static CrudService<Categorie, Long> categorieService;
    private static CrudService<Produit, Long> produitService;
    private static CrudService<Client, Long> clientService;
    private static CrudService<Commande, Long> commandeService;

    @BeforeClass
    public static void setUp() {
        categorieService = new CrudService<>(Categorie.class);
        produitService = new CrudService<>(Produit.class);
        clientService = new CrudService<>(Client.class);
        commandeService = new CrudService<>(Commande.class);
    }

    @Test
    public void testCreateProduitEtCategorie() {
        Categorie cat = new Categorie("Électroménager");
        cat = categorieService.create(cat);
        assertNotNull(cat.getId());

        Produit p = new Produit("Machine à café Expresso", new BigDecimal("349.99"), cat);
        p = produitService.create(p);
        assertNotNull(p.getId());

        Produit found = produitService.getById(p.getId());
        assertEquals("Machine à café Expresso", found.getLibelle());
        assertEquals(cat.getId(), found.getCategorie().getId());
    }

    @Test
    public void testJoinedInheritanceClient() {
        Client client = new Client("Karim Tazi", "karim.tazi@example.ma");
        client = clientService.create(client);
        assertNotNull(client.getId());

        Client found = clientService.getById(client.getId());
        assertEquals("Karim Tazi", found.getNom());
        assertNotNull(found.getDateInscription());
    }

    @Test
    public void testCommandeManyToManyAssociative() {
        Categorie cat = new Categorie("Bureautique");
        cat = categorieService.create(cat);

        Produit p1 = new Produit("Bureau ergonomique", new BigDecimal("499.00"), cat);
        Produit p2 = new Produit("Chaise de bureau confort", new BigDecimal("199.00"), cat);
        p1 = produitService.create(p1);
        p2 = produitService.create(p2);

        Client client = new Client("Salma Bennani", "salma.b@example.ma");
        client = clientService.create(client);

        Commande cmd = new Commande(client);
        cmd.addProduit(p1, 2);
        cmd.addProduit(p2, 1);
        cmd = commandeService.create(cmd);
        assertNotNull(cmd.getId());

        Commande foundCmd = commandeService.getById(cmd.getId());
        assertEquals(2, foundCmd.getLignes().size());
    }

    @Test
    public void testPaginationEtRechercheCriteria() {
        Categorie cat = new Categorie("Stockage & Cloud");
        cat = categorieService.create(cat);

        for (int i = 1; i <= 5; i++) {
            Produit p = new Produit("Disque SSD " + i + "TB", new BigDecimal(80 * i), cat);
            produitService.create(p);
        }

        // Test Pagination
        List<Produit> page = produitService.findPage(1, 3);
        assertEquals(3, page.size());

        // Test Criteria
        List<Produit> filtres = produitService.searchProduits("SSD", new BigDecimal("150.00"), new BigDecimal("350.00"));
        assertTrue(filtres.size() >= 2);
    }
}
