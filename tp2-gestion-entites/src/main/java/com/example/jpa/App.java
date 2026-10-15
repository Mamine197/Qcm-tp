package com.example.jpa;

import com.example.jpa.entities.Salle;
import com.example.jpa.entities.Utilisateur;
import com.example.jpa.service.ServiceGestion;

public class App {
    public static void main(String[] args) {
        System.out.println("=== TP 2 : Gestion des Entités Salle et Utilisateur ===");
        ServiceGestion service = new ServiceGestion();

        try {
            Salle s1 = new Salle("Salle Einstein", 25);
            Salle s2 = new Salle("Salle Turing", 40);
            service.ajouterSalle(s1);
            service.ajouterSalle(s2);

            Utilisateur u1 = new Utilisateur("Amine Nabil", "amine@example.com", s1);
            Utilisateur u2 = new Utilisateur("Sarah Benali", "sarah@example.com", s1);
            Utilisateur u3 = new Utilisateur("Karim Alami", "karim@example.com", s2);
            service.ajouterUtilisateur(u1);
            service.ajouterUtilisateur(u2);
            service.ajouterUtilisateur(u3);

            System.out.println("\n--- Salles disponibles ---");
            service.listerSalles().forEach(System.out::println);

            System.out.println("\n--- Utilisateurs enregistrés ---");
            service.listerUtilisateurs().forEach(System.out::println);

            System.out.println("\n--- Utilisateurs de la Salle Einstein ---");
            service.utilisateursParSalle("Salle Einstein").forEach(System.out::println);

            System.out.println("\n--- Salles de capacité >= 30 ---");
            service.sallesParCapaciteMin(30).forEach(System.out::println);
        } finally {
            service.fermer();
        }
    }
}