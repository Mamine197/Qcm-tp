package com.example.service;

import com.example.model.Auteur;
import com.example.model.Livre;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;

/**
 * Service d'initialisation des données de test du catalogue littéraire.
 */
public class DataInitService {

    private final EntityManager em;

    public DataInitService(EntityManager em) {
        this.em = em;
    }

    public void initData() {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Auteur 1 : Victor Hugo
            Auteur hugo = new Auteur("Hugo", "Victor", "Française", 1802);
            hugo.setBiographie("Poète, dramaturge, écrivain et homme politique français.");
            hugo.addLivre(new Livre("Les Misérables", "978-2070409228", "Roman classique", 1862, new BigDecimal("14.50")));
            hugo.addLivre(new Livre("Notre-Dame de Paris", "978-2253009689", "Roman gothique", 1831, new BigDecimal("12.00")));
            hugo.addLivre(new Livre("Les Contemplations", "978-2070321285", "Poésie", 1856, new BigDecimal("9.90")));
            em.persist(hugo);

            // Auteur 2 : Albert Camus
            Auteur camus = new Auteur("Camus", "Albert", "Française", 1913);
            camus.setBiographie("Écrivain, philosophe et journaliste français, prix Nobel 1957.");
            camus.addLivre(new Livre("L'Étranger", "978-2070360024", "Philosophique", 1942, new BigDecimal("8.50")));
            camus.addLivre(new Livre("La Peste", "978-2070360420", "Roman d'apprentissage", 1947, new BigDecimal("11.20")));
            camus.addLivre(new Livre("Le Mythe de Sisyphe", "978-2070322886", "Essai", 1942, new BigDecimal("9.00")));
            em.persist(camus);

            // Auteur 3 : Émile Zola
            Auteur zola = new Auteur("Zola", "Émile", "Française", 1840);
            zola.setBiographie("Écrivain et journaliste français, chef de file du naturalisme.");
            zola.addLivre(new Livre("Germinal", "978-2253004226", "Naturalisme", 1885, new BigDecimal("10.50")));
            zola.addLivre(new Livre("L'Assommoir", "978-2070410712", "Naturalisme", 1877, new BigDecimal("10.00")));
            zola.addLivre(new Livre("Au Bonheur des Dames", "978-2253005827", "Naturalisme", 1883, new BigDecimal("11.00")));
            em.persist(zola);

            // Auteur 4 : Antoine de Saint-Exupéry
            Auteur stExupery = new Auteur("de Saint-Exupéry", "Antoine", "Française", 1900);
            stExupery.setBiographie("Écrivain, poète, aviateur et reporter français.");
            stExupery.addLivre(new Livre("Le Petit Prince", "978-2070612758", "Conte philosophique", 1943, new BigDecimal("7.90")));
            stExupery.addLivre(new Livre("Vol de nuit", "978-2070360048", "Récit d'aventure", 1931, new BigDecimal("8.00")));
            em.persist(stExupery);

            // Auteur 5 : Jules Verne
            Auteur verne = new Auteur("Verne", "Jules", "Française", 1828);
            verne.setBiographie("Écrivain français dont une grande partie de l'œuvre est consacrée aux voyages extraordinaires.");
            verne.addLivre(new Livre("Vingt Mille Lieues sous les mers", "978-2253006329", "Science-fiction", 1870, new BigDecimal("11.50")));
            verne.addLivre(new Livre("Le Tour du monde en 80 jours", "978-2253005957", "Aventure", 1872, new BigDecimal("9.50")));
            em.persist(verne);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de l'initialisation des données de test", e);
        }
    }
}
