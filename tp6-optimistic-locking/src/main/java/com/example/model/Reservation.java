package com.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité représentant une réservation de salle de réunion.
 * Utilise @Version pour la gestion de la concurrence optimiste (Optimistic Locking).
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String salle;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    @Column(length = 200)
    private String motif;

    @Column(length = 100)
    private String reservePar;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutReservation statut = StatutReservation.CONFIRMEE;

    @Version
    private Long version;

    public Reservation() {
    }

    public Reservation(String salle, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.salle = salle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.motif = "Réunion de travail";
        this.reservePar = "Mohamed Amine";
        this.statut = StatutReservation.CONFIRMEE;
    }

    public Reservation(String salle, LocalDateTime dateDebut, LocalDateTime dateFin, String motif, String reservePar) {
        this.salle = salle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.motif = motif;
        this.reservePar = reservePar;
        this.statut = StatutReservation.CONFIRMEE;
    }

    public Long getId() {
        return id;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getReservePar() {
        return reservePar;
    }

    public void setReservePar(String reservePar) {
        this.reservePar = reservePar;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", salle='" + salle + '\'' +
                ", motif='" + motif + '\'' +
                ", reservePar='" + reservePar + '\'' +
                ", statut=" + statut +
                ", version=" + version +
                '}';
    }
}