package com.example.service;

import com.example.model.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service métier pour les réservations : persistance, requêtes JPQL JOIN FETCH et Criteria API.
 */
public class ReservationService {

    private static final Logger LOGGER = Logger.getLogger(ReservationService.class.getName());
    private final EntityManager em;

    public ReservationService(EntityManager em) {
        this.em = em;
    }

    public void createReservation(Reservation reservation) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(reservation);
            tx.commit();
            LOGGER.info("Création de réservation confirmée pour : " + reservation.getUtilisateur().getEmail());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            LOGGER.severe("Échec lors de la création de la réservation : " + e.getMessage());
            throw e;
        }
    }

    public List<Reservation> findReservationsBySalleAndDate(String nomSalle, LocalDateTime date) {
        String jpql = "SELECT r FROM Reservation r " +
                "JOIN FETCH r.salle " +
                "JOIN FETCH r.utilisateur " +
                "WHERE r.salle.nom = :nomSalle AND r.dateDebut >= :date " +
                "ORDER BY r.dateDebut ASC";

        return em.createQuery(jpql, Reservation.class)
                .setParameter("nomSalle", nomSalle)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Reservation> searchReservationsCriteria(String nomSalle, LocalDateTime date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);

        Predicate pSalle = cb.equal(root.get("salle").get("nom"), nomSalle);
        Predicate pDate = cb.greaterThanOrEqualTo(root.get("dateDebut"), date);

        cq.select(root).where(cb.and(pSalle, pDate));
        return em.createQuery(cq).getResultList();
    }

    public List<Reservation> findReservationsBySalleCacheable(String nomSalle) {
        String jpql = "SELECT r FROM Reservation r WHERE r.salle.nom = :nomSalle";
        return em.createQuery(jpql, Reservation.class)
                .setParameter("nomSalle", nomSalle)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
    }

    public void updateReservationMotif(Long reservationId, String nouveauMotif) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Reservation res = em.find(Reservation.class, reservationId);
            if (res != null) {
                res.setMotif(nouveauMotif);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
