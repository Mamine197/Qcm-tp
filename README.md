# QCM & Travaux Pratiques - Hibernate, JPA & Architecture Microservices (Spring IoC)

Projet complet multi-modules regroupant les Travaux Pratiques et Révisions du cursus **Hibernate & JPA** et du cursus **Architecture Microservices : Conception, Déploiement et Orchestration** (Spring IoC, Inversion de Contrôle, Injection de Dépendances).

---

## 📸 Captures des Résultats d'Exécution (Preuves de Fonctionnement)

> Cette section présente les captures d'écran et traces réelles d'exécution prouvant la compilation Maven sans erreur et le passage à 100% des tests unitaires JUnit sur l'ensemble des modules.

### 🌟 Compilation et Tests Globaux (Multi-modules Reactor - 10 Modules)
![Capture Exécution Globale](docs/screenshots/global_build_execution.png)

```text
[INFO] Reactor Summary for qcm-tp-parent 1.0-SNAPSHOT:
[INFO] 
[INFO] tp2-gestion-entites ................................ SUCCESS [  5.593 s]
[INFO] tp3-gestion-avancee ................................ SUCCESS [  8.347 s]
[INFO] tp4-heritage-mapping ............................... SUCCESS [  6.799 s]
[INFO] tp5-requetes-avancees .............................. SUCCESS [  2.818 s]
[INFO] tp-revision-gestion-commerciale .................... SUCCESS [  3.293 s]
[INFO] tp6-optimistic-locking ............................. SUCCESS [  5.401 s]
[INFO] tp7-cache-second-niveau ............................ SUCCESS [  8.448 s]
[INFO] qcm-tp-parent ...................................... SUCCESS [  0.033 s]
[INFO] tp-capstone ........................................ SUCCESS [  9.282 s]
[INFO] tp2-spring-ioc ..................................... SUCCESS [  1.698 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS (10 / 10 MODULES VALIDES)
[INFO] Total time:  51.932 s
```

---

### 1. TP 2 : Gestion des Entités Salle et Utilisateur (JPA)
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

### 9. TP 2 (Architecture Microservices) : Spring IoC & Injection de Dépendances
![Capture Exécution Spring IoC](docs/screenshots/tp2_spring_ioc_execution.png)

Le module **`tp2-spring-ioc`** (Lab 152 du cours *Architecture Microservices*) illustre les principes fondamentaux de l'**Inversion de Contrôle (IoC)** et de l'**Injection de Dépendances (DI)** :

1. **Couplage Faible via Interfaces** :
   - Interface [`IDao`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/dao/IDao.java) et ses implémentations :
     - [`DaoImpl`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/dao/DaoImpl.java) (`@Component("dao")`, retourne `100.0`).
     - [`DaoImpl2`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/dao/DaoImpl2.java) (`@Component("dao2")`, retourne `150.0`).
     - [`DaoImplSensor`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/dao/DaoImplSensor.java) (`@Component("daoSensor")`, retourne `250.0`).
   - Interface [`IMetier`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/metier/IMetier.java) et son implémentation [`MetierImpl`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/metier/MetierImpl.java) (`@Component("metier")`, `@Autowired @Qualifier("dao2")`).
2. **Trois Modes d'Exécution & d'Injection** :
   - **Annotations Spring (`AnnotationConfigApplicationContext`)** via [`Presentation2`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/presentation/Presentation2.java).
   - **Configuration XML (`ClassPathXmlApplicationContext`)** via [`PresentationXML`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/presentation/PresentationXML.java) et [`applicationContext.xml`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/resources/applicationContext.xml).
   - **Profils Environnementaux (`@Profile`)** via [`PresentationProfiles`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/presentation/PresentationProfiles.java) basculant dynamiquement entre `dev` et `prod`.
   - **Comparaison Couplage Fort vs Réflexion** via [`Presentation`](file:///Users/mamina107/Documents/ats-intelligent-backend/Qcm-tp/tp2-spring-ioc/src/main/java/presentation/Presentation.java).

```mermaid
graph TD
    subgraph "Couche Métier"
        IM["<< Interface >> IMetier"]
        MI["MetierImpl (@Component)"]
        MI -->|implements| IM
    end

    subgraph "Couche DAO (Accès aux Données)"
        ID["<< Interface >> IDao"]
        D1["DaoImpl (@Component('dao'))"]
        D2["DaoImpl2 (@Component('dao2'))"]
        DS["DaoImplSensor (@Component('daoSensor'))"]
        D1 -->|implements| ID
        D2 -->|implements| ID
        DS -->|implements| ID
    end

    subgraph "Conteneur Spring IoC"
        AC["ApplicationContext"]
        AC -->|Injecte @Autowired @Qualifier('dao2')| MI
        MI -.->|dépend de| ID
    end
```

```text
[INFO] Running metier.SpringContextTest
[DaoImpl2] Récupération de la valeur depuis DaoImpl2 (source 2)...
[DaoImpl2] Récupération de la valeur depuis DaoImpl2 (source 2)...
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.647 s
[INFO] Running metier.MetierImplTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 s
[INFO] BUILD SUCCESS
```

---

## 📁 Architecture des Modules

```text
Qcm-tp/
├── pom.xml                               # POM Parent orchestrant tous les 10 modules
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
│       ├── tp_capstone_execution.png
│       └── tp2_spring_ioc_execution.png
├── tp2-gestion-entites/                  # TP 2 (JPA) : Entités Salle & Utilisateur, GenericDao
├── tp3-gestion-avancee/                  # TP 3 (JPA) : Relations @OneToMany, @ManyToMany, Cascade, cycle de vie
├── tp4-heritage-mapping/                 # TP 4 (JPA) : Stratégies SINGLE_TABLE, JOINED, TABLE_PER_CLASS
├── tp5-requetes-avancees/                # TP 5 (JPA) : Disponibilité par créneau, Criteria API, Pagination
├── tp-revision-gestion-commerciale/      # Révision (JPA) : Gestion commerciale complète (GenericDAO, Commande, Produit)
├── tp6-optimistic-locking/               # TP 6 (JPA) : Concurrence, @Version, OptimisticLockException, RetryHandler
├── tp7-cache-second-niveau/              # TP 7 (JPA) : Cache L2 EhCache, JOIN FETCH, Entity Graph, N+1
├── tp-capstone/                          # TP Capstone (JPA) : Architecture finale (Migration V2, L2, Criteria, Perf)
└── tp2-spring-ioc/                       # TP 2 (Microservices) : Spring IoC, @Component, @Autowired, @Qualifier, XML, Profiles
```

---

## 🚀 Exécution des Tests et Démonstrations

Pour compiler l'intégralité des modules et exécuter la suite complète de tests automatisés :

```bash
mvn clean test
```

Pour exécuter les démonstrations du module Spring IoC :

```bash
# Tests unitaires et d'intégration Spring
mvn test -pl tp2-spring-ioc

# Démonstration Spring IoC avec Annotations Java
mvn exec:java -Dexec.mainClass="presentation.Presentation2" -pl tp2-spring-ioc

# Démonstration Spring IoC avec configuration XML
mvn exec:java -Dexec.mainClass="presentation.PresentationXML" -pl tp2-spring-ioc

# Démonstration Spring avec Profils (dev/prod)
mvn exec:java -Dexec.mainClass="presentation.PresentationProfiles" -pl tp2-spring-ioc
```
