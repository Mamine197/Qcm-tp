package com.example.service;

import com.example.model.Auteur;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Service d'analyse et de benchmark comparant les performances :
 * 1. Problème N+1 (Lazy loading standard sans jointure)
 * 2. Résolution par JOIN FETCH JPQL
 * 3. Résolution par Entity Graph déclaratif JPA
 */
public class PerformanceTestService {

    private final EntityManager em;

    public PerformanceTestService(EntityManager em) {
        this.em = em;
    }

    /**
     * Exécution standard sans JOIN FETCH : déclenche 1 requête pour les auteurs
     * puis N requêtes supplémentaires pour charger la collection de chaque auteur.
     */
    public List<Auteur> testPerformanceSansCache() {
        System.out.println(">>> [Benchmark] Exécution sans JOIN FETCH (Déclenchement du problème N+1) <<<");
        long start = System.nanoTime();

        TypedQuery<Auteur> query = em.createQuery("SELECT a FROM Auteur a", Auteur.class);
        List<Auteur> auteurs = query.getResultList();

        int totalLivresCharges = 0;
        for (Auteur a : auteurs) {
            totalLivresCharges += a.getLivres().size(); // Force l'initialisation de chaque collection (requête N)
        }

        long durationMs = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("[Résultat N+1] %d auteurs et %d livres chargés en %d ms (1 + %d requêtes SQL).%n",
                auteurs.size(), totalLivresCharges, durationMs, auteurs.size());
        return auteurs;
    }

    /**
     * Résolution avec JOIN FETCH : charge en une seule requête SQL les auteurs et leurs livres.
     * Utilisation de DISTINCT pour éviter la démultiplication des lignes côté JPA.
     */
    public List<Auteur> testPerformanceAvecJoinFetch() {
        System.out.println(">>> [Benchmark] Exécution avec JOIN FETCH (Suppression N+1 en 1 requête) <<<");
        long start = System.nanoTime();

        TypedQuery<Auteur> query = em.createQuery("SELECT DISTINCT a FROM Auteur a JOIN FETCH a.livres", Auteur.class);
        List<Auteur> auteurs = query.getResultList();

        int totalLivresCharges = 0;
        for (Auteur a : auteurs) {
            totalLivresCharges += a.getLivres().size(); // Déjà en mémoire, 0 requête SQL supplémentaire
        }

        long durationMs = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("[Résultat JOIN FETCH] %d auteurs et %d livres chargés en %d ms (1 seule requête SQL).%n",
                auteurs.size(), totalLivresCharges, durationMs);
        return auteurs;
    }

    /**
     * Résolution avec Entity Graph : optimisation déclarative sans altérer la requête JPQL.
     */
    public List<Auteur> testPerformanceAvecEntityGraph() {
        System.out.println(">>> [Benchmark] Exécution avec Entity Graph JPA <<<");
        long start = System.nanoTime();

        EntityGraph<?> entityGraph = em.getEntityGraph("Auteur.livres");
        TypedQuery<Auteur> query = em.createQuery("SELECT a FROM Auteur a", Auteur.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Auteur> auteurs = query.getResultList();

        int totalLivresCharges = 0;
        for (Auteur a : auteurs) {
            totalLivresCharges += a.getLivres().size(); // Déjà chargé par l'Entity Graph
        }

        long durationMs = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("[Résultat Entity Graph] %d auteurs et %d livres chargés en %d ms (chargement optimisé).%n",
                auteurs.size(), totalLivresCharges, durationMs);
        return auteurs;
    }
}
