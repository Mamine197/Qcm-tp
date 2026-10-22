package com.example.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "salles")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.example.model.Salle")
public class Salle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String nom;

    @Column(length = 50)
    private String numero;

    @Column(nullable = false)
    private int capacite;

    @Column(length = 255)
    private String description;

    private int etage = 1;

    @Version
    private int version;

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    public Salle() {
    }

    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    public Salle(String nom, int capacite, String description) {
        this.nom = nom;
        this.capacite = capacite;
        this.description = description;
    }

    public Salle(String nom, String numero, int capacite, String description, int etage) {
        this.nom = nom;
        this.numero = numero;
        this.capacite = capacite;
        this.description = description;
        this.etage = etage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setSalle(this);
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", numero='" + numero + '\'' +
                ", capacite=" + capacite +
                ", etage=" + etage +
                '}';
    }
}
