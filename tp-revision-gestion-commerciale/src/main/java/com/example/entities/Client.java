package com.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client extends User {

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    private String email;

    public Client() {
        super();
        this.dateInscription = LocalDate.now();
    }

    public Client(String nom, String email) {
        super(nom);
        this.email = email;
        this.dateInscription = LocalDate.now();
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
