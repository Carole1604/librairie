-- Script pour corriger la base de données existante

-- 1. Ajouter les colonnes manquantes
ALTER TABLE livre ADD COLUMN IF NOT EXISTS editeur VARCHAR(150);
ALTER TABLE description ADD COLUMN IF NOT EXISTS mots_cles TEXT;

-- 2. Supprimer les données existantes pour repartir proprement
DELETE FROM exemplaire;
DELETE FROM description;
DELETE FROM livre;
DELETE FROM inscription;
DELETE FROM adherent;

-- 3. Réinsérer les adhérents avec la contrainte respectée
INSERT INTO adherent (nom, prenom, login, mot_de_passe, email, telephone, adresse, date_naissance, est_etudiant, est_professionnel, est_anonyme, quota_max, quota_actuel) 
VALUES 
('Martin', 'Sophie', 'sophie.martin', 'pass123', 'sophie.martin@email.com', '0123456789', '123 Rue de la Paix, Paris', '1990-05-15', true, false, false, 5, 0),
('Bernard', 'Pierre', 'pierre.bernard', 'pass456', 'pierre.bernard@email.com', '0987654321', '456 Avenue des Champs, Lyon', '1985-08-22', false, true, false, 3, 0);

-- 4. Insérer les livres
INSERT INTO livre (titre, auteur, isbn, annee_publication, editeur, nombre_exemplaires, age_minimum) 
VALUES 
('Le Petit Prince', 'Antoine de Saint-Exupéry', '9782070612758', 1943, 'Gallimard', 2, 8),
('1984', 'George Orwell', '9782070368228', 1949, 'Gallimard', 1, 16),
('Le Seigneur des Anneaux', 'J.R.R. Tolkien', '9782070612881', 1954, 'Gallimard', 1, 12);

-- 5. Insérer les descriptions
INSERT INTO description (id_livre, resume, mots_cles, categorie) 
VALUES 
(1, 'Un conte poétique et philosophique sous l''apparence d''un livre pour enfants.', 'conte, philosophie, enfance', 'Littérature jeunesse'),
(2, 'Un roman d''anticipation dystopique décrivant une société totalitaire.', 'dystopie, totalitarisme, surveillance', 'Science-fiction'),
(3, 'Une épopée fantastique dans un monde imaginaire appelé la Terre du Milieu.', 'fantasy, épopée, aventure', 'Fantasy');

-- 6. Insérer les exemplaires
INSERT INTO exemplaire (id_livre, nom_exemplaire, etat, date_acquisition, localisation, code_barre) 
VALUES 
(1, 'EX001', 'disponible', '2023-01-15', 'Rayon A', 'BAR001'),
(1, 'EX002', 'disponible', '2023-01-15', 'Rayon A', 'BAR002'),
(2, 'EX003', 'disponible', '2023-02-20', 'Rayon B', 'BAR003'),
(3, 'EX004', 'disponible', '2023-03-10', 'Rayon C', 'BAR004');

-- 7. Insérer les inscriptions
INSERT INTO inscription (id_adherent, date_inscription, statut_inscription) 
VALUES 
(1, '2024-01-15', 'actif'),
(2, '2024-02-01', 'actif');

-- 8. Vérification des données
SELECT 'Administrateurs:' as info, COUNT(*) as nombre FROM administrateur
UNION ALL
SELECT 'Adhérents:', COUNT(*) FROM adherent
UNION ALL
SELECT 'Livres:', COUNT(*) FROM livre
UNION ALL
SELECT 'Exemplaires:', COUNT(*) FROM exemplaire
UNION ALL
SELECT 'Descriptions:', COUNT(*) FROM description
UNION ALL
SELECT 'Inscriptions:', COUNT(*) FROM inscription; 