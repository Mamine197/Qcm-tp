package com.example.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entité Auteur configurée pour le cache de second niveau (EhCache)
 * et l'optimisation des chargements de relations via Entity Graph.
 */
@Entity
@Table(name = "auteurs")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(
        name = "Auteur.livres",
        attributeNodes = @NamedAttributeNode("livres")
)
public class Auteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nom;

    @Column(length = 80)
    private String prenom;

    @Column(length = 60)
    private String nationalite;

    private Integer anneeNaissance;

    @Column(length = 500)
    private String biographie;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Livre> livres = new ArrayList<>();

    public Auteur() {
    }

    public Auteur(String nom) {
        this.nom = nom;
    }

    public Auteur(String nom, String prenom, String nationalite, Integer anneeNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.anneeNaissance = anneeNaissance;
        this.dateCreation = LocalDateTime.now();
    }

    // Gestion bidirectionnelle synchronisée
    public void addLivre(Livre livre) {
        if (livre != null) {
            this.livres.add(livre);
            livre.setAuteur(this);
        }
    }

    public void removeLivre(Livre livre) {
        if (livre != null) {
            this.livres.remove(livre);
            livre.setAuteur(null);
        }
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public Integer getAnneeNaissance() {
        return anneeNaissance;
    }

    public void setAnneeNaissance(Integer anneeNaissance) {
        this.anneeNaissance = anneeNaissance;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }

    public int getNombreLivres() {
        return livres != null ? livres.size() : 0;
    }

    @Override
    public String toString() {
        return "Auteur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", nbLivres=" + getNombreLivres() +
                '}';
    }
}
