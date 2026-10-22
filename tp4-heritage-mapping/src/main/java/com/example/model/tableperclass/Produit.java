package com.example.model.tableperclass;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private Double prix;

    private LocalDateTime dateCreation;

    public Produit() {
        this.dateCreation = LocalDateTime.now();
    }

    public Produit(String nom) {
        this.nom = nom;
        this.prix = 0.0;
        this.dateCreation = LocalDateTime.now();
    }

    public Produit(String nom, Double prix) {
        this.nom = nom;
        this.prix = prix;
        this.dateCreation = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}