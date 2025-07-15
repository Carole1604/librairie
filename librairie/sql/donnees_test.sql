-- Script de données de test pour la bibliothèque
-- À exécuter après la création des tables

-- 1. Données de paramétrage
INSERT INTO parametrage_general (
    duree_pret_etudiant,
    duree_pret_professionnel,
    duree_pret_anonyme,
    quota_max_etudiant,
    quota_max_professionnel,
    quota_max_anonyme,
    duree_prolongation,
    nombre_prolongations_max
) VALUES (
    10,  -- 10 jours pour les étudiants
    15,  -- 15 jours pour les professionnels
    5,   -- 5 jours pour les anonymes
    3,   -- 3 livres max pour les étudiants
    6,   -- 6 livres max pour les professionnels
    1,   -- 1 livre max pour les anonymes
    7,   -- 7 jours de prolongation
    1    -- 1 prolongation maximum
) ON CONFLICT (id) DO NOTHING;

-- 2. Adhérents de test
INSERT INTO adherent (nom, prenom, login, mot_de_passe, email, est_etudiant, est_professionnel, est_anonyme, quota_max, quota_actuel, est_actif) VALUES
('Dupont', 'Jean', 'jean.dupont', 'password123', 'jean.dupont@email.com', true, false, false, 3, 0, true),
('Martin', 'Marie', 'marie.martin', 'password123', 'marie.martin@email.com', false, true, false, 6, 0, true),
('Durand', 'Pierre', 'pierre.durand', 'password123', 'pierre.durand@email.com', false, false, true, 1, 0, true),
('Leroy', 'Sophie', 'sophie.leroy', 'password123', 'sophie.leroy@email.com', true, false, false, 3, 0, true),
('Moreau', 'Paul', 'paul.moreau', 'password123', 'paul.moreau@email.com', false, true, false, 6, 0, true);

-- 3. Livres de test
INSERT INTO livre (titre, auteur, isbn, annee_publication, genre, description) VALUES
('Le Petit Prince', 'Antoine de Saint-Exupéry', '9782070612758', 1943, 'Roman', 'Un conte poétique et philosophique'),
('1984', 'George Orwell', '9782070368228', 1949, 'Science-fiction', 'Un roman d''anticipation dystopique'),
('Harry Potter à l''école des sorciers', 'J.K. Rowling', '9782070541270', 1997, 'Fantasy', 'Le premier tome de la série Harry Potter'),
('Le Seigneur des Anneaux', 'J.R.R. Tolkien', '9782070612881', 1954, 'Fantasy', 'Une épopée fantastique'),
('Don Quichotte', 'Miguel de Cervantes', '9782070413111', 1605, 'Roman', 'Un roman picaresque espagnol'),
('Madame Bovary', 'Gustave Flaubert', '9782070413112', 1857, 'Roman', 'Un roman réaliste français'),
('Les Misérables', 'Victor Hugo', '9782070413113', 1862, 'Roman', 'Un roman historique français'),
('Le Comte de Monte-Cristo', 'Alexandre Dumas', '9782070413114', 1844, 'Roman', 'Un roman d''aventure français');

-- 4. Exemplaires de test
INSERT INTO exemplaire (id_livre, numero_exemplaire, etat, date_acquisition) VALUES
(1, 'EX001', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(1, 'EX002', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(2, 'EX003', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(2, 'EX004', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(3, 'EX005', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(3, 'EX006', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(4, 'EX007', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(4, 'EX008', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(5, 'EX009', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(5, 'EX010', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(6, 'EX011', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(7, 'EX012', 'disponible', CURRENT_DATE - INTERVAL '1 year'),
(8, 'EX013', 'disponible', CURRENT_DATE - INTERVAL '1 year');

-- 5. Prêts de test (pour les 7 derniers jours)
INSERT INTO pret (id_adherent, id_exemplaire, date_debut, date_fin_prevue, type_pret, est_prolonge) VALUES
(1, 1, CURRENT_DATE - INTERVAL '6 days', CURRENT_DATE + INTERVAL '4 days', 'domicile', false),
(2, 3, CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE + INTERVAL '10 days', 'domicile', false),
(3, 5, CURRENT_DATE - INTERVAL '4 days', CURRENT_DATE - INTERVAL '1 day', 'domicile', false),
(1, 7, CURRENT_DATE - INTERVAL '3 days', CURRENT_DATE + INTERVAL '7 days', 'domicile', false),
(2, 9, CURRENT_DATE - INTERVAL '2 days', CURRENT_DATE + INTERVAL '13 days', 'domicile', false),
(4, 11, CURRENT_DATE - INTERVAL '1 day', CURRENT_DATE + INTERVAL '9 days', 'domicile', false),
(5, 13, CURRENT_DATE, CURRENT_DATE + INTERVAL '15 days', 'domicile', false);

-- 6. Retours de test
UPDATE pret SET date_rendu_reelle = CURRENT_DATE - INTERVAL '2 days' WHERE id = 3;
UPDATE pret SET date_rendu_reelle = CURRENT_DATE - INTERVAL '1 day' WHERE id = 5;

-- 7. Réservations de test
INSERT INTO reservation (id_adherent, id_livre, date_reservation, statut) VALUES
(1, 2, CURRENT_DATE - INTERVAL '3 days', 'en_attente'),
(2, 4, CURRENT_DATE - INTERVAL '2 days', 'en_attente'),
(3, 6, CURRENT_DATE - INTERVAL '1 day', 'en_attente'),
(4, 8, CURRENT_DATE, 'en_attente');

-- 8. Pénalités de test
INSERT INTO penalite (id_adherent, id_pret, date_debut, date_fin, nombre_jours_retard, est_active) VALUES
(3, 3, CURRENT_DATE - INTERVAL '1 day', CURRENT_DATE + INTERVAL '1 day', 1, true);

-- 9. Statistiques de test (pour les 7 derniers jours)
INSERT INTO tableau_de_bord (date_stat, nombre_prets, nombre_retours, nombre_reservations, nombre_penalites, nombre_adherents_actifs, nombre_livres_disponibles) VALUES
(CURRENT_DATE - INTERVAL '6 days', 1, 0, 0, 0, 5, 11),
(CURRENT_DATE - INTERVAL '5 days', 1, 0, 0, 0, 5, 10),
(CURRENT_DATE - INTERVAL '4 days', 1, 0, 1, 0, 5, 9),
(CURRENT_DATE - INTERVAL '3 days', 1, 0, 1, 0, 5, 8),
(CURRENT_DATE - INTERVAL '2 days', 1, 1, 1, 0, 5, 9),
(CURRENT_DATE - INTERVAL '1 day', 1, 1, 1, 1, 5, 9),
(CURRENT_DATE, 1, 0, 1, 0, 5, 8)
ON CONFLICT (date_stat) DO UPDATE SET
    nombre_prets = EXCLUDED.nombre_prets,
    nombre_retours = EXCLUDED.nombre_retours,
    nombre_reservations = EXCLUDED.nombre_reservations,
    nombre_penalites = EXCLUDED.nombre_penalites,
    nombre_adherents_actifs = EXCLUDED.nombre_adherents_actifs,
    nombre_livres_disponibles = EXCLUDED.nombre_livres_disponibles;

-- 10. Mise à jour des quotas des adhérents
UPDATE adherent SET quota_actuel = (
    SELECT COUNT(*) FROM pret 
    WHERE pret.id_adherent = adherent.id_adherent 
    AND pret.date_rendu_reelle IS NULL
);

-- Vérification des données insérées
SELECT 'Paramétrage' as type, COUNT(*) as nombre FROM parametrage_general
UNION ALL
SELECT 'Adhérents', COUNT(*) FROM adherent
UNION ALL
SELECT 'Livres', COUNT(*) FROM livre
UNION ALL
SELECT 'Exemplaires', COUNT(*) FROM exemplaire
UNION ALL
SELECT 'Prêts', COUNT(*) FROM pret
UNION ALL
SELECT 'Réservations', COUNT(*) FROM reservation
UNION ALL
SELECT 'Pénalités', COUNT(*) FROM penalite
UNION ALL
SELECT 'Statistiques', COUNT(*) FROM tableau_de_bord;

-- Affichage des données de test
SELECT '=== DONNÉES DE TEST CRÉÉES ===' as info;

SELECT 'Adhérents de test :' as info;
SELECT nom, prenom, login, est_etudiant, est_professionnel, est_anonyme, quota_max, quota_actuel 
FROM adherent ORDER BY nom;

SELECT 'Livres de test :' as info;
SELECT titre, auteur, genre FROM livre ORDER BY titre;

SELECT 'Prêts actifs :' as info;
SELECT a.nom, a.prenom, l.titre, p.date_debut, p.date_fin_prevue
FROM pret p
JOIN adherent a ON p.id_adherent = a.id_adherent
JOIN exemplaire e ON p.id_exemplaire = e.id_exemplaire
JOIN livre l ON e.id_livre = l.id_livre
WHERE p.date_rendu_reelle IS NULL
ORDER BY p.date_debut DESC;

SELECT 'Statistiques récentes :' as info;
SELECT date_stat, nombre_prets, nombre_retours, nombre_reservations, nombre_penalites
FROM tableau_de_bord
ORDER BY date_stat DESC
LIMIT 7; 