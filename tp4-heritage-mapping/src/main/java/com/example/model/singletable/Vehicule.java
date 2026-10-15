package com.example.model.singletable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehicules")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicule_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marque;

    private LocalDate anneeFabrication;

    public Vehicule() {}

    public Vehicule(String marque) {
        this.marque = marque;
        this.anneeFabrication = LocalDate.now();
    }

    public Vehicule(String marque, LocalDate anneeFabrication) {
        this.marque = marque;
        this.anneeFabrication = anneeFabrication;
    }

    public Long getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public LocalDate getAnneeFabrication() {
        return anneeFabrication;
    }

    public void setAnneeFabrication(LocalDate anneeFabrication) {
        this.anneeFabrication = anneeFabrication;
    }
}