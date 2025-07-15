-- Script pour ajouter la table recherche manquante
-- Exécuter ce script sur votre base de données PostgreSQL

CREATE TABLE IF NOT EXISTS recherche (
    id_recherche SERIAL PRIMARY KEY,
    critere VARCHAR(255),
    date_recherche TIMESTAMP,
    id_adherent INT REFERENCES adherent(id_adherent) ON DELETE CASCADE
);

-- Commentaire pour documenter la table
COMMENT ON TABLE recherche IS 'Table pour stocker l''historique des recherches des adhérents'; 