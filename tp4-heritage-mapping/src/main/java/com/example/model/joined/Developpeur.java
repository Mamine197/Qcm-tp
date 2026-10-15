package com.example.model.joined;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "developpeurs")
public class Developpeur extends Employe {

    private String langage;
    private String specialite;
    private Integer anneeExperience;

    public Developpeur() {}

    public Developpeur(String nom, String langage) {
        super(nom);
        this.langage = langage;
        this.specialite = "Backend";
        this.anneeExperience = 3;
    }

    public Developpeur(String nom, String prenom, String email, String langage, String specialite, Integer anneeExperience) {
        super(nom, prenom, email);
        this.langage = langage;
        this.specialite = specialite;
        this.anneeExperience = anneeExperience;
    }

    public String getLangage() {
        return langage;
    }

    public void setLangage(String langage) {
        this.langage = langage;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Integer getAnneeExperience() {
        return anneeExperience;
    }

    public void setAnneeExperience(Integer anneeExperience) {
        this.anneeExperience = anneeExperience;
    }
}