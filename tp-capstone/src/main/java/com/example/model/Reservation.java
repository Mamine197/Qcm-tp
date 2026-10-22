package com.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    @Column(length = 200)
    private String motif = "Réunion de travail";

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private StatutReservation statut = StatutReservation.CONFIRMEE;

    @Version
    private int version;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    public Reservation() {
    }

    public Reservation(Salle salle, Utilisateur utilisateur, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.salle = salle;
        this.utilisateur = utilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Reservation(Salle salle, Utilisateur utilisateur, LocalDateTime dateDebut, LocalDateTime dateFin, String motif, StatutReservation statut) {
        this.salle = salle;
        this.utilisateur = utilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.motif = motif;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", salle=" + (salle != null ? salle.getNom() : null) +
                ", user=" + (utilisateur != null ? utilisateur.getEmail() : null) +
                ", debut=" + dateDebut +
                ", fin=" + dateFin +
                ", statut=" + statut +
                ", version=" + version +
                '}';
    }
}
