package com.example.model.tableperclass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "livres")
public class Livre extends Produit {

    private String auteur;
    private String isbn;

    public Livre() {
        super();
    }

    public Livre(String nom, String auteur) {
        super(nom);
        this.auteur = auteur;
        this.isbn = "978-2070360024";
    }

    public Livre(String nom, Double prix, String auteur, String isbn) {
        super(nom, prix);
        this.auteur = auteur;
        this.isbn = isbn;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}