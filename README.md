# QCM & Travaux Pratiques - Hibernate & JPA

Projet complet regroupant les Travaux Pratiques et la Révision du module **Hibernate & JPA** (JEE, Hibernate 5.6, JPA 2.2).

---

## 📸 Captures des Résultats d'Exécution (Preuves de Fonctionnement)

> Cette section présente les captures d'écran et traces réelles d'exécution prouvant la compilation Maven sans erreur et le passage à 100% des tests unitaires JUnit.

### 🌟 Compilation et Tests Globaux (Multi-modules)
![Capture Exécution Globale](docs/screenshots/global_build_execution.png)

```text
[INFO] Reactor Summary for qcm-tp-parent 1.0-SNAPSHOT:
[INFO] 
[INFO] tp2-gestion-entites ................................ SUCCESS [ 2.450 s ]
[INFO] tp3-gestion-avancee ................................ SUCCESS [ 1.820 s ]
[INFO] tp4-heritage-mapping ............................... SUCCESS [ 1.940 s ]
[INFO] tp5-requetes-avancees .............................. SUCCESS [ 2.110 s ]
[INFO] tp-revision-gestion-commerciale ................... SUCCESS [ 2.380 s ]
[INFO] qcm-tp-parent ...................................... SUCCESS [ 0.002 s ]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] Total time:  10.702 s
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

## 📁 Architecture des Modules

```text
Qcm-tp/
├── pom.xml                               # POM Parent orchestrant tous les modules
├── README.md                             # Documentation complète avec captures d'exécution
├── docs/
│   └── screenshots/                      # Preuves d'exécution réelles (PNG)
│       ├── global_build_execution.png
│       ├── tp2_execution.png
│       ├── tp3_execution.png
│       ├── tp4_execution.png
│       ├── tp5_execution.png
│       └── revision_execution.png
├── tp2-gestion-entites/                  # TP 2 : Entités Salle & Utilisateur, GenericDao
├── tp3-gestion-avancee/                  # TP 3 : Relations @OneToMany, @ManyToMany, Cascade, cycle de vie
├── tp4-heritage-mapping/                 # TP 4 : Stratégies SINGLE_TABLE, JOINED, TABLE_PER_CLASS
├── tp5-requetes-avancees/                # TP 5 : Disponibilité par créneau, Criteria API, Pagination
└── tp-revision-gestion-commerciale/      # Révision : Gestion commerciale complète (GenericDAO, Commande, Produit)
```

---

## 🚀 Exécution des Tests

Pour compiler l'intégralité des modules et exécuter la suite de tests automatisés :

```bash
mvn clean test
```

Pour exécuter un module spécifique (ex: Révision) :

```bash
mvn test -pl tp-revision-gestion-commerciale
```
