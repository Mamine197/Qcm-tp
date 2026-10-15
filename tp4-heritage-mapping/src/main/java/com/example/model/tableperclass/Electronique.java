package com.example.model.tableperclass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "electroniques")
public class Electronique extends Produit {

    private Integer garantie; // durée en mois
    private String marque;

    public Electronique() {
        super();
    }

    public Electronique(String nom, int garantie) {
        super(nom);
        this.garantie = garantie;
        this.marque = "TechBrand";
    }

    public Electronique(String nom, Double prix, Integer garantie, String marque) {
        super(nom, prix);
        this.garantie = garantie;
        this.marque = marque;
    }

    public Integer getGarantie() {
        return garantie;
    }

    public void setGarantie(Integer garantie) {
        this.garantie = garantie;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }
}