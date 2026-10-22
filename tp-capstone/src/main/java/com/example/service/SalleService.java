package com.example.service;

import com.example.model.Salle;
import com.example.model.StatutReservation;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service métier dédié à la gestion des salles et à la recherche de disponibilités.
 */
public class SalleService {

    private static final Logger LOGGER = Logger.getLogger(SalleService.class.getName());
    private final EntityManager em;

    public SalleService(EntityManager em) {
        this.em = em;
    }

    public void enregistrerSalle(Salle salle) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(salle);
            tx.commit();
            LOGGER.info("Salle enregistrée : " + salle.getNom());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Salle findById(Long id) {
        return em.find(Salle.class, id);
    }

    /**
     * Recherche des salles disponibles entre deux dates (aucune réservation active qui chevauche).
     */
    public List<Salle> findSallesDisponibles(LocalDateTime dateDebut, LocalDateTime dateFin) {
        String jpql = "SELECT s FROM Salle s WHERE s.id NOT IN (" +
                "  SELECT r.salle.id FROM Reservation r " +
                "  WHERE r.statut <> :statutAnnule " +
                "  AND r.dateDebut < :dateFin " +
                "  AND r.dateFin > :dateDebut" +
                ") ORDER BY s.capacite ASC";

        return em.createQuery(jpql, Salle.class)
                .setParameter("statutAnnule", StatutReservation.ANNULEE)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }

    public List<Salle> findSallesParCapaciteMinimale(int capaciteMin) {
        String jpql = "SELECT s FROM Salle s WHERE s.capacite >= :cap ORDER BY s.capacite ASC";
        return em.createQuery(jpql, Salle.class)
                .setParameter("cap", capaciteMin)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
    }
}
