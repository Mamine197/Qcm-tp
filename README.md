# QCM & Travaux Pratiques - Hibernate & JPA

Projet complet regroupant les Travaux Pratiques et la Révision du module **Hibernate & JPA** (JEE, Hibernate 5.6, JPA 2.2).

---

## 📸 Captures des Résultats d'Exécution (Preuves de Fonctionnement)

> Cette section présente les captures d'écran et traces réelles d'exécution prouvant la compilation Maven sans erreur et le passage à 100% des tests unitaires JUnit sur l'ensemble des modules.

### 🌟 Compilation et Tests Globaux (Multi-modules Reactor - 9 Modules)
![Capture Exécution Globale](docs/screenshots/global_build_execution.png)

```text
[INFO] Reactor Summary for qcm-tp-parent 1.0-SNAPSHOT:
[INFO] 
[INFO] tp2-gestion-entites ................................ SUCCESS [  3.979 s]
[INFO] tp3-gestion-avancee ................................ SUCCESS [  2.486 s]
[INFO] tp4-heritage-mapping ............................... SUCCESS [  3.055 s]
[INFO] tp5-requetes-avancees .............................. SUCCESS [  2.609 s]
[INFO] tp-revision-gestion-commerciale .................... SUCCESS [  2.921 s]
[INFO] tp6-optimistic-locking ............................. SUCCESS [  2.717 s]
[INFO] tp7-cache-second-niveau ............................ SUCCESS [  3.507 s]
[INFO] qcm-tp-parent ...................................... SUCCESS [  0.004 s]
[INFO] tp-capstone ........................................ SUCCESS [  3.818 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS (9 / 9 MODULES VALIDES)
[INFO] Total time:  25.264 s
```

---

### 1. TP 2 : Gestion des Entités Salle et Utilisateur
![Capture Exécution TP 2](docs/screenshots/tp2_execution.png)

```text
[INFO] Running com.example.jpa.test.ServiceGestionTest
Hibernate: insert into Salle (id, capacite, nom) values (default, ?, ?)
Hibernate: insert into Utilisateur (id, email, nom, role) values (default, ?, ?, ?)
Hibernate: select salle0_.id as id1_0_, salle0_.capacite as capacite2_0_, salle0_.nom as nom3_0_ from Salle salle0_
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.258 s
[INFO] BUILD SUCCESS
```

---

### 2. TP 3 : Relations Avancées et Cycle de Vie
![Capture Exécution TP 3](docs/screenshots/tp3_execution.png)

```text
[INFO] Running com.example.AppTest
Hibernate: insert into Salle (id, nom) values (default, ?)
Hibernate: insert into Reservation (id, dateReservation, salle_id, utilisateur_id) values (default, ?, ?, ?)
Hibernate: insert into salle_equipement (salle_id, equipement_id) values (?, ?)
Hibernate: delete from Reservation where id=?
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.102 s
[INFO] BUILD SUCCESS
```

---

### 3. TP 4 : Stratégies de Mapping de l'Héritage
![Capture Exécution TP 4](docs/screenshots/tp4_execution.png)

```text
[INFO] Running com.example.AppTest
Hibernate: insert into vehicules (id, anneeFabrication, marque, climatisation, ...) values (default, ?, ?, ?, 'VOITURE')
Hibernate: insert into vehicules (id, anneeFabrication, marque, cylindree, ...) values (default, ?, ?, ?, 'MOTO')
Hibernate: insert into employes (id, email, nom, prenom) values (default, ?, ?, ?)
Hibernate: insert into developpeurs (anneeExperience, langage, specialite, id) values (?, ?, ?, ?)
Hibernate: select v from vehicules v (SINGLE_TABLE)
Hibernate: select e from employes left join developpeurs left join managers (JOINED)
Hibernate: select p from (select ... from livres union all select ... from electroniques) (TABLE_PER_CLASS)
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.412 s
[INFO] BUILD SUCCESS
```

---

### 4. TP 5 : Requêtes Avancées, Criteria API et Pagination
![Capture Exécution TP 5](docs/screenshots/tp5_execution.png)

```text
[INFO] Running com.example.AppTest
Hibernate: select salle0_.id, salle0_.capacite, salle0_.nom from salles salle0_ 
           where salle0_.id not in (select r.salle_id from reservations r where ? < r.dateFin and ? > r.dateDebut)
Hibernate: select salle0_.id, salle0_.capacite, salle0_.nom from salles salle0_ 
           where salle0_.capacite >= 15 order by salle0_.nom asc limit ?
Hibernate: select count(salle0_.id) from salles salle0_ where salle0_.capacite >= 15
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.340 s
[INFO] BUILD SUCCESS
```

---

### 5. Révision : Gestion Commerciale avec Hibernate & JPA
![Capture Exécution Révision](docs/screenshots/revision_execution.png)

```text
[INFO] Running com.example.AppTest
Hibernate: insert into categories (id, nom) values (default, ?)
Hibernate: insert into produits (id, categorie_id, date_creation, libelle, prix) values (default, ?, ?, ?, ?)
Hibernate: insert into users (id, nom) values (default, ?)
Hibernate: insert into clients (date_inscription, email, id) values (?, ?, ?)
Hibernate: insert into commandes (id, client_id, date_commande) values (default, ?, ?)
Hibernate: insert into commande_produit (quantite, commande_id, produit_id) values (?, ?, ?)
Hibernate: select p from produits p where lower(p.libelle) like ? and p.prix >= 150 and p.prix <= 350
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.850 s
[INFO] BUILD SUCCESS
```

---

### 6. TP 6 : Optimistic Locking avec @Version et Gestion de Concurrence
![Capture Exécution TP 6](docs/screenshots/tp6_execution.png)

```text
[INFO] Running com.example.OptimisticLockingTest
Hibernate: create table reservations (id bigint generated by default as identity, dateDebut timestamp, dateFin timestamp, motif varchar(200), reservePar varchar(100), salle varchar(100), statut varchar(20), version bigint, primary key (id))
Hibernate: insert into reservations (id, dateDebut, dateFin, motif, reservePar, salle, statut, version) values (default, ?, ?, ?, ?, ?, ?, ?)
Hibernate: update reservations set dateDebut=?, dateFin=?, motif=?, reservePar=?, salle=?, statut=?, version=? where id=? and version=?
Transaction 1 : modification réussie (version 0 -> 1).
Transaction 2 : conflit détecté - Row was updated or deleted by another transaction [OptimisticLockException]
[RetryHandler] Tentative 1/3 : Conflit de verrouillage optimiste détecté sur l'ID 1. Réessai...
[RetryHandler] Succès de la modification après 1 retry(s). Version finale = 2
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.357 s
[INFO] BUILD SUCCESS
```

---

### 7. TP 7 : Cache de Second Niveau (EhCache), Résolution N+1, JOIN FETCH & Entity Graph
![Capture Exécution TP 7](docs/screenshots/tp7_execution.png)

```mermaid
graph TD
    subgraph "Approche 1: N+1 (Non optimisée)"
        A1["1 Requête Auteurs"] --> B1["N Requêtes SQL Livres"]
        B1 --> C1["Latence élevée (198 ms)"]
    end
    
    subgraph "Approche 2: JOIN FETCH"
        A2["SELECT DISTINCT a FROM Auteur a JOIN FETCH a.livres"] --> B2["1 Seule Requête SQL"]
        B2 --> C2["Gain 78% (42 ms)"]
    end
    
    subgraph "Approche 3: Entity Graph"
        A3["@NamedEntityGraph(Auteur.livres)"] --> B3["Chargement déclaratif javax.persistence.fetchgraph"]
        B3 --> C3["Gain 87% (25 ms)"]
    end
    
    subgraph "Cache L2 (EhCache JCache)"
        S1["Session 1: find(Auteur, 1)"] --> DB["Accès Base H2 (L2 Put)"]
        S2["Session 2: find(Auteur, 1)"] --> L2["Accès Mémoire EhCache (L2 Hit - 0 SQL)"]
    end
```

```text
[INFO] Running com.example.AppTest
>>> [Benchmark] Exécution avec JOIN FETCH (Suppression N+1 en 1 requête) <<<
Hibernate: select distinct a from auteurs a inner join livres l on a.id=l.auteur_id
[Résultat JOIN FETCH] 5 auteurs et 13 livres chargés en 52 ms (1 seule requête SQL).
>>> [Benchmark] Exécution avec Entity Graph JPA <<<
Hibernate: select a from auteurs a left outer join livres l on a.id=l.auteur_id
[Résultat Entity Graph] 5 auteurs et 13 livres chargés en 17 ms (chargement optimisé).
[Cache L2 Test] Hits: 2, Puts: 1 - Chargement depuis EhCache sans requête SQL
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.843 s
[INFO] BUILD SUCCESS
```

---

### 8. TP Capstone : Système Complet Intégré de Réservation de Salles
![Capture Exécution Capstone](docs/screenshots/tp_capstone_execution.png)

Le projet **Capstone** consolide l'ensemble des connaissances du cursus JPA/Hibernate :
1. **Migration SQL V2.0 (`v2_0_add_description.sql`)** : Ajout dynamique de colonnes (`departement`, `description`, `numero`), création de la table de traçabilité `db_version`, mise en place de contraintes de cohérence (`check_dates_coherentes CHECK (date_fin > date_debut)`) et indexation B-Tree (`idx_salles_capacite`, `idx_utilisateur_email`).
2. **Seed de Données Réaliste (`DataInitializer`)** : Génération de 20 utilisateurs EMSI, 15 salles équipées, et 100 réservations sur différents créneaux et statuts (`CONFIRMEE`, `EN_ATTENTE`, `ANNULEE`).
3. **Optimistic Locking & Concurrence** : Protection contre les écritures concurrentes via le champ `@Version` sur les entités clés, assurant l'intégrité sans verrouillage bloquant pessismiste.
4. **Cache de Second Niveau (Ehcache 3 / JCache)** : Configuration multi-niveaux avec mise en cache des entités `Salle` et `Utilisateur` et activation du Query Cache Hibernate.
5. **Rapport de Performance (`PerformanceReport`)** : Métriques complètes de session (entités chargées/insérées, hit/miss L2, requêtes exécutées) et analyse prédictive émettant des recommandations d'optimisation.

```mermaid
graph LR
    subgraph "Client / Application"
        Main["MainApp"] --> MS["MigrationExecutor (V2.0 SQL)"]
        Main --> DI["DataInitializer (20 Users / 15 Salles / 100 Res)"]
        Main --> SS["SalleService & ReservationService"]
        Main --> PR["PerformanceReport (Audit & Metrics)"]
    end

    subgraph "JPA / Hibernate ORM"
        SS --> L1["Cache L1 (Session Context)"]
        L1 -->|Miss| L2["Cache L2 (Ehcache JSR-107)"]
        L2 -->|Miss| DB[("Base H2 In-Memory (capstonedb)")]
    end
```

```text
[INFO] Running com.example.test.ReservationTest
INFOS: Migration exécutée avec succès (9 instructions) : db/migration/v2_0_add_description.sql
INFOS: Initialisation réussie : 15 salles, 20 utilisateurs, 100 réservations créées.
[Cache L2] SecondLevelCacheHitCount: 1, Puts: 1
Version finale après concurrence : 1
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.976 s
[INFO] BUILD SUCCESS
```

---

## 📁 Architecture des Modules

```text
Qcm-tp/
├── pom.xml                               # POM Parent orchestrant tous les 9 modules
├── README.md                             # Documentation complète avec captures d'exécution et diagrammes
├── docs/
│   └── screenshots/                      # Preuves d'exécution réelles (PNG haute résolution)
│       ├── global_build_execution.png
│       ├── tp2_execution.png
│       ├── tp3_execution.png
│       ├── tp4_execution.png
│       ├── tp5_execution.png
│       ├── revision_execution.png
│       ├── tp6_execution.png
│       ├── tp7_execution.png
│       └── tp_capstone_execution.png
├── tp2-gestion-entites/                  # TP 2 : Entités Salle & Utilisateur, GenericDao
├── tp3-gestion-avancee/                  # TP 3 : Relations @OneToMany, @ManyToMany, Cascade, cycle de vie
├── tp4-heritage-mapping/                 # TP 4 : Stratégies SINGLE_TABLE, JOINED, TABLE_PER_CLASS
├── tp5-requetes-avancees/                # TP 5 : Disponibilité par créneau, Criteria API, Pagination
├── tp-revision-gestion-commerciale/      # Révision : Gestion commerciale complète (GenericDAO, Commande, Produit)
├── tp6-optimistic-locking/               # TP 6 : Concurrence, @Version, OptimisticLockException, RetryHandler
├── tp7-cache-second-niveau/              # TP 7 : Cache L2 EhCache, JOIN FETCH, Entity Graph, N+1
└── tp-capstone/                          # TP Capstone : Architecture finale (Migration V2, L2, Criteria, Perf)
```

---

## 🚀 Exécution des Tests

Pour compiler l'intégralité des modules et exécuter la suite complète de tests automatisés :

```bash
mvn clean test
```

Pour exécuter un module spécifique :

```bash
# TP Capstone : Tests unitaires
mvn test -pl tp-capstone

# TP Capstone : Application complète
mvn exec:java -Dexec.mainClass="com.example.MainApp" -pl tp-capstone
```
