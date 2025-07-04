-- Données de test pour la bibliothèque

-- Administrateur
INSERT INTO administrateur (login, mot_de_passe, nom, prenom, email) VALUES
('admin', 'admin123', 'Administrateur', 'Principal', 'admin@bibliotheque.com');

-- Statuts
INSERT INTO statut (nom_statut) VALUES
('en_cours'),
('valide'),
('refuse');

-- Jours fériés
INSERT INTO jour_ferie (nom_jour_ferie, date_ferie) VALUES
('Nouvel An', '2025-01-01'),
('Fête du Travail', '2025-05-01'),
('Fête Nationale', '2025-07-14'),
('Noël', '2025-12-25');

-- Adhérents
INSERT INTO adherent (nom, prenom, login, mot_de_passe, email, telephone, adresse, date_naissance, est_etudiant, est_professionnel, est_anonyme, quota_max, quota_actuel) VALUES
('Martin', 'Sophie', 'sophie.martin', 'pass123', 'sophie.martin@email.com', '0123456789', '123 Rue de la Paix, Paris', '1990-05-15', true, false, false, 5, 0),
('Bernard', 'Pierre', 'pierre.bernard', 'pass456', 'pierre.bernard@email.com', '0987654321', '456 Avenue des Champs, Lyon', '1985-08-22', false, true, false, 3, 0);

-- Inscriptions
INSERT INTO inscription (id_adherent, date_inscription, statut_inscription) VALUES
(1, '2024-01-15', 'actif'),
(2, '2024-02-01', 'actif');

-- Livres
INSERT INTO livre (titre, isbn, edition, auteur, annee_publication, editeur, nombre_exemplaires, age_minimum) VALUES
('Le Petit Prince', '9782070612758', '1ère', 'Antoine de Saint-Exupéry', 1943, 'Gallimard', 2, 8),
('1984', '9782070368228', '1ère', 'George Orwell', 1949, 'Gallimard', 1, 16),
('Le Seigneur des Anneaux', '9782070612881', '1ère', 'J.R.R. Tolkien', 1954, 'Gallimard', 1, 12);

-- Descriptions
INSERT INTO description (id_livre, resume, langue, nombre_pages, categorie, editeur, mots_cles) VALUES
(1, 'Un conte poétique et philosophique sous l''apparence d''un livre pour enfants.', 'Français', 96, 'Littérature jeunesse', 'Gallimard', 'conte, philosophie, enfance'),
(2, 'Un roman d''anticipation dystopique décrivant une société totalitaire.', 'Français', 376, 'Science-fiction', 'Gallimard', 'dystopie, totalitarisme, surveillance'),
(3, 'Une épopée fantastique dans un monde imaginaire appelé la Terre du Milieu.', 'Français', 1216, 'Fantasy', 'Gallimard', 'fantasy, épopée, aventure');

-- Exemplaires
INSERT INTO exemplaire (nom_exemplaire, id_livre, etat, date_acquisition, localisation, code_barre) VALUES
('EX001', 1, 'disponible', '2023-01-15', 'Rayon A', 'BAR001'),
('EX002', 1, 'disponible', '2023-01-15', 'Rayon A', 'BAR002'),
('EX003', 2, 'disponible', '2023-02-20', 'Rayon B', 'BAR003'),
('EX004', 3, 'disponible', '2023-03-10', 'Rayon C', 'BAR004');

-- Réservations
INSERT INTO reservation (id_adherent, id_livre, date_reservation, id_statut) VALUES
(1, 1, '2025-06-01', 1),
(2, 2, '2025-06-02', 1);

-- Prêts
INSERT INTO pret (type_pret, date_debut, date_fin_prevue, id_adherent, id_exemplaire, id_statut, duree_pret_jours, est_prolonge) VALUES
('domicile', '2025-06-01', '2025-06-15', 1, 1, 1, 14, false),
('domicile', '2025-06-02', '2025-06-16', 2, 3, 1, 14, false);

-- Prolongements
INSERT INTO prolongement_pret (id_pret, date_demande, nouvelle_date_rendu, id_statut, nombre_jours_ajoutes) VALUES
(1, '2025-06-10', '2025-06-22', 1, 7);

-- Pénalités
INSERT INTO penalite (date_debut, date_fin, id_pret, id_adherent, montant, motif, nombre_jours_retard, est_active) VALUES
('2025-06-20', '2025-06-27', 1, 1, 5.00, 'Retard de retour', 7, true);

-- Tableau de bord (exemple)
INSERT INTO tableau_de_bord (date_stat, nombre_prets, nombre_retours, nombre_reservations, nombre_penalites, nombre_adherents_actifs, nombre_livres_disponibles) VALUES
('2025-07-01', 2, 0, 2, 1, 2, 3); 