package com.example.model.joined;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "managers")
public class Manager extends Employe {

    private String service;
    private Integer nombreSubordonnes;
    private Double bonus;

    public Manager() {}

    public Manager(String nom, int nombreSubordonnes) {
        super(nom);
        this.nombreSubordonnes = nombreSubordonnes;
        this.service = "Direction Technique";
        this.bonus = 5000.0;
    }

    public Manager(String nom, String prenom, String email, String service, Integer nombreSubordonnes, Double bonus) {
        super(nom, prenom, email);
        this.service = service;
        this.nombreSubordonnes = nombreSubordonnes;
        this.bonus = bonus;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getNombreSubordonnes() {
        return nombreSubordonnes;
    }

    public void setNombreSubordonnes(Integer nombreSubordonnes) {
        this.nombreSubordonnes = nombreSubordonnes;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }
}