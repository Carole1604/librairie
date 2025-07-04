-- Données de test pour la bibliothèque

-- Insertion d'un administrateur
INSERT INTO administrateur (login, mot_de_passe, nom, prenom, email) 
VALUES ('admin', 'admin123', 'Administrateur', 'Principal', 'admin@bibliotheque.com');

-- Insertion d'adhérents de test
INSERT INTO adherent (nom, prenom, login, mot_de_passe, email, telephone, adresse, date_naissance, est_etudiant, est_professionnel, quota_max, quota_actuel) 
VALUES 
('Martin', 'Sophie', 'sophie.martin', 'pass123', 'sophie.martin@email.com', '0123456789', '123 Rue de la Paix, Paris', '1990-05-15', true, false, 5, 0),
('Bernard', 'Pierre', 'pierre.bernard', 'pass456', 'pierre.bernard@email.com', '0987654321', '456 Avenue des Champs, Lyon', '1985-08-22', false, true, 3, 0);

-- Insertion de livres de test
INSERT INTO livre (titre, auteur, isbn, annee_publication, editeur, nombre_pages, prix) 
VALUES 
('Le Petit Prince', 'Antoine de Saint-Exupéry', '9782070612758', 1943, 'Gallimard', 96, 6.50),
('1984', 'George Orwell', '9782070368228', 1949, 'Gallimard', 376, 8.90),
('Le Seigneur des Anneaux', 'J.R.R. Tolkien', '9782070612881', 1954, 'Gallimard', 1216, 25.00);

-- Insertion de descriptions
INSERT INTO description (id_livre, resume, mots_cles, categorie) 
VALUES 
(1, 'Un conte poétique et philosophique sous l''apparence d''un livre pour enfants.', 'conte, philosophie, enfance', 'Littérature jeunesse'),
(2, 'Un roman d''anticipation dystopique décrivant une société totalitaire.', 'dystopie, totalitarisme, surveillance', 'Science-fiction'),
(3, 'Une épopée fantastique dans un monde imaginaire appelé la Terre du Milieu.', 'fantasy, épopée, aventure', 'Fantasy');

-- Insertion d'exemplaires
INSERT INTO exemplaire (id_livre, numero_exemplaire, statut, date_acquisition, prix_acquisition) 
VALUES 
(1, 'EX001', 'DISPONIBLE', '2023-01-15', 6.50),
(1, 'EX002', 'DISPONIBLE', '2023-01-15', 6.50),
(2, 'EX003', 'DISPONIBLE', '2023-02-20', 8.90),
(3, 'EX004', 'DISPONIBLE', '2023-03-10', 25.00);

-- Insertion de statuts
INSERT INTO statut (nom, description) 
VALUES 
('DISPONIBLE', 'L''exemplaire est disponible pour le prêt'),
('EMPRUNTE', 'L''exemplaire est actuellement emprunté'),
('RESERVE', 'L''exemplaire est réservé'),
('EN_REPARATION', 'L''exemplaire est en cours de réparation'),
('PERDU', 'L''exemplaire a été perdu');

-- Insertion de jours fériés
INSERT INTO jour_ferie (date, nom, description) 
VALUES 
('2025-01-01', 'Nouvel An', 'Jour de l''an'),
('2025-05-01', 'Fête du Travail', 'Fête internationale du travail'),
('2025-05-08', 'Victoire 1945', 'Victoire des Alliés sur l''Allemagne nazie'),
('2025-07-14', 'Fête Nationale', 'Prise de la Bastille'),
('2025-08-15', 'Assomption', 'Assomption de Marie'),
('2025-11-01', 'Toussaint', 'Fête de tous les saints'),
('2025-11-11', 'Armistice', 'Armistice de 1918'),
('2025-12-25', 'Noël', 'Nativité de Jésus-Christ'); 