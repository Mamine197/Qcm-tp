# QCM & TPs - Hibernate & JPA

Ce dépôt contient les travaux pratiques du cours **Hibernate & JPA**.

## TP 2 : Gestion des Entités Salle et Utilisateur avec JPA et Hibernate

### 📌 Objectifs
- Configuration de l'unité de persistance JPA avec le fournisseur Hibernate (`RESOURCE_LOCAL`).
- Base de données H2 embarquée en mémoire.
- Modélisation des entités `Salle` et `Utilisateur` avec annotations JPA (`@Entity`, `@Id`, `@GeneratedValue`, `@Column`, `@ManyToOne`, `@JoinColumn`).
- Implémentation d'un pattern DAO avec `GenericDao<T>`, `SalleDao`, et `UtilisateurDao`.
- Gestion manuelle des transactions JPA (`EntityTransaction`).
- Tests unitaires complets avec JUnit 4 (`ServiceGestionTest`).

### 📁 Structure du projet
```text
tp2-gestion-entites/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/example/jpa/
    │   │   ├── entities/
    │   │   │   ├── Salle.java
    │   │   │   └── Utilisateur.java
    │   │   ├── dao/
    │   │   │   ├── GenericDao.java
    │   │   │   ├── SalleDao.java
    │   │   │   └── UtilisateurDao.java
    │   │   ├── service/
    │   │   │   └── ServiceGestion.java
    │   │   └── App.java
    │   └── resources/
    │       └── META-INF/
    │           └── persistence.xml
    └── test/
        └── java/com/example/jpa/test/
            └── ServiceGestionTest.java
```

### 🚀 Compilation et Exécution des Tests

```bash
# Exécuter les tests depuis la racine ou dans le sous-dossier
mvn clean test
```
