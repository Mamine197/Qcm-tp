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
# Compiler et exécuter tous les tests
mvn clean test
```

---

## TP 3 : Relations Avancées et Cycle de Vie des Entités avec JPA et Hibernate

### 📌 Objectifs
- Gestion des relations complexes JPA :
  - `@OneToMany` et `@ManyToOne` bi-directionnel avec `mappedBy` et `orphanRemoval = true`.
  - `@ManyToMany` avec table de jointure explicite (`salle_equipement`).
- Propagation des opérations en cascade (`CascadeType.PERSIST`, `CascadeType.MERGE`).
- Cycle de vie des entités JPA (`transient`, `managed`, `detached`, `removed`).
- Chargement paresseux (`FetchType.LAZY`) vs immédiat (`FetchType.EAGER`).
- Tests unitaires complets avec JUnit 4 (`AppTest`).

### 📁 Structure du projet
```text
tp3-gestion-avancee/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/example/
    │   │   ├── model/
    │   │   │   ├── Salle.java
    │   │   │   ├── Utilisateur.java
    │   │   │   ├── Reservation.java
    │   │   │   └── Equipement.java
    │   │   └── App.java
    │   └── resources/
    │       └── META-INF/
    │           └── persistence.xml
    └── test/
        └── java/com/example/
            └── AppTest.java
```

---

## TP 4 : Héritage avec Hibernate - Stratégies de Mapping

### 📌 Objectifs
- Mise en œuvre des trois stratégies d'héritage JPA/Hibernate :
  - **SINGLE_TABLE** : hiérarchie `Vehicule` (`Voiture`, `Moto`) avec colonne discriminante `@DiscriminatorColumn(name = "vehicule_type")`.
  - **JOINED** : hiérarchie normalisée `Employe` (`Developpeur`, `Manager`) avec jointures de clés primaires/étrangères.
  - **TABLE_PER_CLASS** : hiérarchie `Produit` (`Livre`, `Electronique`) avec tables indépendantes et requêtes polymorphiques par `UNION ALL`.
- Validations Bean Validation (`@NotNull`, `@Min`, `@Max`, `@Email`).
- Requêtes JPQL polymorphiques et analyse des requêtes SQL générées.
- Tests unitaires complets avec JUnit 4 (`AppTest`).

### 📁 Structure du projet
```text
tp4-heritage-mapping/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/example/
    │   │   ├── model/
    │   │   │   ├── singletable/ (Vehicule, Voiture, Moto)
    │   │   │   ├── joined/      (Employe, Developpeur, Manager)
    │   │   │   └── tableperclass/ (Produit, Livre, Electronique)
    │   │   └── App.java
    │   └── resources/
    │       └── META-INF/
    │           └── persistence.xml
    └── test/
        └── java/com/example/
            └── AppTest.java
```


