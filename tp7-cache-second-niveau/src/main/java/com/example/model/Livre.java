package com.example.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entité Livre liée à un Auteur (ManyToOne Lazy)
 * et optimisée pour le cache de second niveau L2.
 */
@Entity
@Table(name = "livres")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Livre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String titre;

    @Column(length = 30)
    private String isbn;

    @Column(length = 60)
    private String genre;

    private Integer anneePublication;

    @Column(precision = 8, scale = 2)
    private BigDecimal prix;

    private boolean disponible = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auteur_id", nullable = false)
    private Auteur auteur;

    public Livre() {
    }

    public Livre(String titre) {
        this.titre = titre;
    }

    public Livre(String titre, String isbn, String genre, Integer anneePublication, BigDecimal prix) {
        this.titre = titre;
        this.isbn = isbn;
        this.genre = genre;
        this.anneePublication = anneePublication;
        this.prix = prix;
        this.disponible = true;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", isbn='" + isbn + '\'' +
                ", genre='" + genre + '\'' +
                ", prix=" + prix +
                '}';
    }
}
