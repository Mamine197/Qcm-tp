package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "students")
@Schema(description = "Entité représentant un étudiant")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de l'étudiant", example = "1")
    private int id;

    @Schema(description = "Nom de l'étudiant", example = "LACHGAR")
    private String nom;

    @Schema(description = "Prénom de l'étudiant", example = "Mohamed")
    private String prenom;

    @Temporal(TemporalType.DATE)
    @Schema(description = "Date de naissance de l'étudiant", example = "1985-09-01")
    private Date dateNaissance;

    public Student() {
    }

    public Student(int id, String nom, String prenom, Date dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public Student(String nom, String prenom, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
