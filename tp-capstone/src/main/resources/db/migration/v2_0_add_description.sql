-- ====================================================================
-- Script de migration V2_0 : Enrichissement du schéma et contraintes
-- Application de gestion de réservation des salles (TP Capstone)
-- ====================================================================

-- 1. Table de versionnement du schéma de base de données
CREATE TABLE IF NOT EXISTS db_version (
    id INT PRIMARY KEY,
    version VARCHAR(50) NOT NULL,
    date_mise_a_jour TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

MERGE INTO db_version (id, version, date_mise_a_jour) KEY(id) VALUES (1, '2.0', CURRENT_TIMESTAMP);

-- 2. Évolution de la table 'salles' : description et numéro extrait
ALTER TABLE salles ADD COLUMN IF NOT EXISTS description VARCHAR(255);
ALTER TABLE salles ADD COLUMN IF NOT EXISTS numero VARCHAR(50);
UPDATE salles SET numero = SUBSTRING(nom, 7) WHERE nom LIKE 'Salle %' AND (numero IS NULL OR numero = '');

-- 3. Évolution de la table 'utilisateurs' : ajout du département
ALTER TABLE utilisateurs ADD COLUMN IF NOT EXISTS departement VARCHAR(100);

-- 4. Index de performance pour les filtres fréquents
CREATE UNIQUE INDEX IF NOT EXISTS idx_utilisateur_email ON utilisateurs(email);
CREATE INDEX IF NOT EXISTS idx_salles_capacite ON salles(capacite);

-- 5. Contraintes de cohérence métier sur les réservations
ALTER TABLE reservations ADD CONSTRAINT IF NOT EXISTS check_dates_coherentes CHECK (date_fin > date_debut);
