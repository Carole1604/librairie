-- Script pour créer la table tableau_de_bord
-- Stocke les statistiques quotidiennes de la bibliothèque

-- Supprimer la table si elle existe
DROP TABLE IF EXISTS tableau_de_bord CASCADE;

-- Créer la table tableau_de_bord
CREATE TABLE tableau_de_bord (
    id_statistique SERIAL PRIMARY KEY,
    date_stat DATE NOT NULL UNIQUE,
    nombre_prets INTEGER NOT NULL DEFAULT 0,
    nombre_retours INTEGER NOT NULL DEFAULT 0,
    nombre_reservations INTEGER NOT NULL DEFAULT 0,
    nombre_penalites INTEGER NOT NULL DEFAULT 0,
    nombre_adherents_actifs INTEGER NOT NULL DEFAULT 0,
    nombre_livres_disponibles INTEGER NOT NULL DEFAULT 0
);

-- Créer un index sur la date pour optimiser les recherches
CREATE INDEX idx_tableau_de_bord_date ON tableau_de_bord(date_stat);

-- Créer un index sur la date pour les requêtes de plage
CREATE INDEX idx_tableau_de_bord_date_range ON tableau_de_bord(date_stat DESC);

-- Insérer des données d'exemple pour les 7 derniers jours
INSERT INTO tableau_de_bord (date_stat, nombre_prets, nombre_retours, nombre_reservations, nombre_penalites, nombre_adherents_actifs, nombre_livres_disponibles) VALUES
(CURRENT_DATE - INTERVAL '6 days', 5, 3, 2, 1, 25, 150),
(CURRENT_DATE - INTERVAL '5 days', 8, 4, 3, 0, 25, 148),
(CURRENT_DATE - INTERVAL '4 days', 6, 7, 1, 2, 26, 149),
(CURRENT_DATE - INTERVAL '3 days', 10, 5, 4, 1, 26, 151),
(CURRENT_DATE - INTERVAL '2 days', 7, 6, 2, 0, 27, 150),
(CURRENT_DATE - INTERVAL '1 day', 9, 8, 3, 1, 27, 149),
(CURRENT_DATE, 0, 0, 0, 0, 27, 150);

-- Vérifier la structure créée
SELECT column_name, data_type, is_nullable, column_default 
FROM information_schema.columns 
WHERE table_name = 'tableau_de_bord' 
ORDER BY ordinal_position;

-- Afficher les données insérées
SELECT * FROM tableau_de_bord ORDER BY date_stat DESC;

-- Créer une vue pour les statistiques consolidées
CREATE OR REPLACE VIEW statistiques_consolidees AS
SELECT 
    date_stat,
    nombre_prets,
    nombre_retours,
    nombre_reservations,
    nombre_penalites,
    nombre_adherents_actifs,
    nombre_livres_disponibles,
    (nombre_prets + nombre_retours + nombre_reservations + nombre_penalites) as total_activite
FROM tableau_de_bord
ORDER BY date_stat DESC;

-- Créer une fonction pour calculer les statistiques automatiquement
CREATE OR REPLACE FUNCTION calculer_statistiques_jour(date_cible DATE)
RETURNS TABLE (
    date_stat DATE,
    nombre_prets INTEGER,
    nombre_retours INTEGER,
    nombre_reservations INTEGER,
    nombre_penalites INTEGER,
    nombre_adherents_actifs INTEGER,
    nombre_livres_disponibles INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        date_cible as date_stat,
        COALESCE((SELECT COUNT(*) FROM pret WHERE DATE(date_debut) = date_cible), 0) as nombre_prets,
        COALESCE((SELECT COUNT(*) FROM pret WHERE DATE(date_rendu_reelle) = date_cible), 0) as nombre_retours,
        COALESCE((SELECT COUNT(*) FROM reservation WHERE DATE(date_reservation) = date_cible), 0) as nombre_reservations,
        COALESCE((SELECT COUNT(*) FROM penalite WHERE DATE(date_debut) = date_cible), 0) as nombre_penalites,
        COALESCE((SELECT COUNT(*) FROM adherent WHERE est_actif = true), 0) as nombre_adherents_actifs,
        COALESCE((SELECT COUNT(*) FROM exemplaire WHERE etat = 'disponible'), 0) as nombre_livres_disponibles;
END;
$$ LANGUAGE plpgsql;

-- Créer un trigger pour mettre à jour automatiquement les statistiques
CREATE OR REPLACE FUNCTION trigger_mise_a_jour_statistiques()
RETURNS TRIGGER AS $$
BEGIN
    -- Mettre à jour les statistiques pour aujourd'hui
    INSERT INTO tableau_de_bord (date_stat, nombre_prets, nombre_retours, nombre_reservations, nombre_penalites, nombre_adherents_actifs, nombre_livres_disponibles)
    SELECT * FROM calculer_statistiques_jour(CURRENT_DATE)
    ON CONFLICT (date_stat) 
    DO UPDATE SET
        nombre_prets = EXCLUDED.nombre_prets,
        nombre_retours = EXCLUDED.nombre_retours,
        nombre_reservations = EXCLUDED.nombre_reservations,
        nombre_penalites = EXCLUDED.nombre_penalites,
        nombre_adherents_actifs = EXCLUDED.nombre_adherents_actifs,
        nombre_livres_disponibles = EXCLUDED.nombre_livres_disponibles;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Créer les triggers sur les tables principales
-- Note: Ces triggers sont optionnels et peuvent être gérés par l'application

-- Trigger sur la table pret
-- CREATE TRIGGER trigger_pret_statistiques
--     AFTER INSERT OR UPDATE OR DELETE ON pret
--     FOR EACH ROW EXECUTE FUNCTION trigger_mise_a_jour_statistiques();

-- Trigger sur la table reservation
-- CREATE TRIGGER trigger_reservation_statistiques
--     AFTER INSERT OR UPDATE OR DELETE ON reservation
--     FOR EACH ROW EXECUTE FUNCTION trigger_mise_a_jour_statistiques();

-- Trigger sur la table penalite
-- CREATE TRIGGER trigger_penalite_statistiques
--     AFTER INSERT OR UPDATE OR DELETE ON penalite
--     FOR EACH ROW EXECUTE FUNCTION trigger_mise_a_jour_statistiques();

-- Afficher les informations sur la table
SELECT 
    'Tableau de bord créé avec succès' as message,
    COUNT(*) as nombre_enregistrements
FROM tableau_de_bord; 