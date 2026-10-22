package com.example.model.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("VOITURE")
public class Voiture extends Vehicule {

    private Integer nombrePortes;
    private Boolean climatisation;
    private String typeCarburant;

    public Voiture() {}

    public Voiture(String marque, int nombrePortes) {
        super(marque);
        this.nombrePortes = nombrePortes;
        this.climatisation = true;
        this.typeCarburant = "Essence";
    }

    public Voiture(String marque, LocalDate annee, Integer nombrePortes, Boolean climatisation, String typeCarburant) {
        super(marque, annee);
        this.nombrePortes = nombrePortes;
        this.climatisation = climatisation;
        this.typeCarburant = typeCarburant;
    }

    public Integer getNombrePortes() {
        return nombrePortes;
    }

    public void setNombrePortes(Integer nombrePortes) {
        this.nombrePortes = nombrePortes;
    }

    public Boolean getClimatisation() {
        return climatisation;
    }

    public void setClimatisation(Boolean climatisation) {
        this.climatisation = climatisation;
    }

    public String getTypeCarburant() {
        return typeCarburant;
    }

    public void setTypeCarburant(String typeCarburant) {
        this.typeCarburant = typeCarburant;
    }
}