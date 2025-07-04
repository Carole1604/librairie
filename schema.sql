-- Script PostgreSQL pour l'application de gestion de bibliothèque
-- Création de la base de données et des tables avec contraintes

-- Suppression des tables si elles existent déjà (ordre inverse des dépendances)
DROP TABLE IF EXISTS penalite CASCADE;
DROP TABLE IF EXISTS prolongement_pret CASCADE;
DROP TABLE IF EXISTS pret CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS exemplaire CASCADE;
DROP TABLE IF EXISTS description CASCADE;
DROP TABLE IF EXISTS livre CASCADE;
DROP TABLE IF EXISTS inscription CASCADE;
DROP TABLE IF EXISTS adherent CASCADE;
DROP TABLE IF EXISTS jour_ferie CASCADE;
DROP TABLE IF EXISTS statut CASCADE;
DROP TABLE IF EXISTS tableau_de_bord CASCADE;

-- Table Statut (pour les prêts et réservations)
CREATE TABLE statut (
    id_statut SERIAL PRIMARY KEY,
    nom_statut VARCHAR(50) NOT NULL UNIQUE CHECK (nom_statut IN ('en_cours', 'valide', 'refuse'))
);

-- Table Jour_ferie
CREATE TABLE jour_ferie (
    id_jour_ferie SERIAL PRIMARY KEY,
    nom_jour_ferie VARCHAR(100) NOT NULL,
    date_ferie DATE NOT NULL UNIQUE
);

-- Table Administrateur
CREATE TABLE administrateur (
    id_administrateur SERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Adherent
CREATE TABLE adherent (
    id_adherent SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    email VARCHAR(150),
    telephone VARCHAR(20),
    adresse TEXT,
    date_naissance DATE,
    est_etudiant BOOLEAN DEFAULT FALSE,
    est_professionnel BOOLEAN DEFAULT FALSE,
    est_anonyme BOOLEAN DEFAULT TRUE,
    quota_max INT DEFAULT 3 CHECK (quota_max > 0),
    quota_actuel INT DEFAULT 0 CHECK (quota_actuel >= 0),
    est_actif BOOLEAN DEFAULT TRUE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_type_adherent CHECK (
        (est_etudiant::int + est_professionnel::int + est_anonyme::int) = 1
    )
);

-- Table Inscription
CREATE TABLE inscription (
    id_inscription SERIAL PRIMARY KEY,
    id_adherent INT NOT NULL REFERENCES adherent(id_adherent) ON DELETE CASCADE,
    date_inscription DATE NOT NULL DEFAULT CURRENT_DATE,
    statut_inscription VARCHAR(20) DEFAULT 'actif' CHECK (statut_inscription IN ('actif', 'suspendu', 'expire'))
);

-- Table Livre
CREATE TABLE livre (
    id_livre SERIAL PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    edition VARCHAR(100),
    auteur VARCHAR(255) NOT NULL,
    annee_publication INT CHECK (annee_publication > 0 AND annee_publication <= EXTRACT(YEAR FROM CURRENT_DATE)),
    nombre_exemplaires INT DEFAULT 0 CHECK (nombre_exemplaires >= 0),
    age_minimum INT DEFAULT 0 CHECK (age_minimum >= 0),
    date_ajout TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Description (informations supplémentaires sur les livres)
CREATE TABLE description (
    id_description SERIAL PRIMARY KEY,
    id_livre INT NOT NULL REFERENCES livre(id_livre) ON DELETE CASCADE,
    resume TEXT,
    langue VARCHAR(50) DEFAULT 'Français',
    nombre_pages INT CHECK (nombre_pages > 0),
    categorie VARCHAR(100),
    editeur VARCHAR(150)
);

-- Table Exemplaire
CREATE TABLE exemplaire (
    id_exemplaire SERIAL PRIMARY KEY,
    nom_exemplaire VARCHAR(100) NOT NULL,
    id_livre INT NOT NULL REFERENCES livre(id_livre) ON DELETE CASCADE,
    etat VARCHAR(20) DEFAULT 'disponible' CHECK (etat IN ('disponible', 'emprunte', 'reserve', 'en_reparation', 'perdu', 'retire')),
    date_acquisition DATE DEFAULT CURRENT_DATE,
    localisation VARCHAR(100),
    code_barre VARCHAR(50) UNIQUE
);

-- Table Reservation
CREATE TABLE reservation (
    id_reservation SERIAL PRIMARY KEY,
    id_adherent INT NOT NULL REFERENCES adherent(id_adherent) ON DELETE CASCADE,
    id_livre INT NOT NULL REFERENCES livre(id_livre) ON DELETE CASCADE,
    date_reservation DATE NOT NULL DEFAULT CURRENT_DATE,
    id_statut INT DEFAULT 1 REFERENCES statut(id_statut), -- 1 = en_cours par défaut
    date_validation DATE,
    date_annulation DATE,
    motif_annulation TEXT,
    CONSTRAINT chk_dates_reservation CHECK (
        date_validation IS NULL OR date_validation >= date_reservation
    )
);

-- Table Pret
CREATE TABLE pret (
    id_pret SERIAL PRIMARY KEY,
    type_pret VARCHAR(20) DEFAULT 'domicile' CHECK (type_pret IN ('domicile', 'sur_lieu')),
    date_debut DATE NOT NULL DEFAULT CURRENT_DATE,
    date_fin_prevue DATE NOT NULL,
    date_rendu_reelle DATE,
    id_adherent INT NOT NULL REFERENCES adherent(id_adherent) ON DELETE CASCADE,
    id_exemplaire INT NOT NULL REFERENCES exemplaire(id_exemplaire) ON DELETE CASCADE,
    id_reservation INT REFERENCES reservation(id_reservation), -- Lien vers la réservation source si applicable
    id_statut INT DEFAULT 1 REFERENCES statut(id_statut), -- 1 = en_cours par défaut
    duree_pret_jours INT DEFAULT 14 CHECK (duree_pret_jours > 0),
    est_prolonge BOOLEAN DEFAULT FALSE,
    CONSTRAINT chk_dates_pret CHECK (
        date_fin_prevue > date_debut AND 
        (date_rendu_reelle IS NULL OR date_rendu_reelle >= date_debut)
    )
);

-- Table Prolongement_pret
CREATE TABLE prolongement_pret (
    id_prolongement SERIAL PRIMARY KEY,
    id_pret INT NOT NULL REFERENCES pret(id_pret) ON DELETE CASCADE,
    date_demande DATE NOT NULL DEFAULT CURRENT_DATE,
    nouvelle_date_rendu DATE NOT NULL,
    id_statut INT DEFAULT 1 REFERENCES statut(id_statut), -- 1 = en_cours par défaut
    date_validation DATE,
    motif_refus TEXT,
    nombre_jours_ajoutes INT,
    CONSTRAINT chk_prolongement CHECK (
        nouvelle_date_rendu > date_demande AND
        (date_validation IS NULL OR date_validation >= date_demande)
    )
);

-- Table Penalite
CREATE TABLE penalite (
    id_penalite SERIAL PRIMARY KEY,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    id_pret INT NOT NULL REFERENCES pret(id_pret) ON DELETE CASCADE,
    id_adherent INT NOT NULL REFERENCES adherent(id_adherent) ON DELETE CASCADE,
    montant DECIMAL(10,2) DEFAULT 0.00,
    motif VARCHAR(255) NOT NULL,
    nombre_jours_retard INT NOT NULL CHECK (nombre_jours_retard > 0),
    est_active BOOLEAN DEFAULT TRUE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_dates_penalite CHECK (date_fin >= date_debut)
);

-- Table Tableau_de_bord (statistiques)
CREATE TABLE tableau_de_bord (
    id_statistique SERIAL PRIMARY KEY,
    date_stat DATE NOT NULL DEFAULT CURRENT_DATE,
    nombre_prets INT DEFAULT 0 CHECK (nombre_prets >= 0),
    nombre_retours INT DEFAULT 0 CHECK (nombre_retours >= 0),
    nombre_reservations INT DEFAULT 0 CHECK (nombre_reservations >= 0),
    nombre_penalites INT DEFAULT 0 CHECK (nombre_penalites >= 0),
    nombre_adherents_actifs INT DEFAULT 0 CHECK (nombre_adherents_actifs >= 0),
    nombre_livres_disponibles INT DEFAULT 0 CHECK (nombre_livres_disponibles >= 0),
    UNIQUE(date_stat)
);

-- Insertion des données de base

-- Statuts
INSERT INTO statut (nom_statut) VALUES 
('en_cours'),
('valide'),
('refuse');

-- Quelques jours fériés français pour exemple
INSERT INTO jour_ferie (nom_jour_ferie, date_ferie) VALUES 
('Nouvel An', '2025-01-01'),
('Lundi de Pâques', '2025-04-21'),
('Fête du Travail', '2025-05-01'),
('Victoire 1945', '2025-05-08'),
('Ascension', '2025-05-29'),
('Lundi de Pentecôte', '2025-06-09'),
('Fête Nationale', '2025-07-14'),
('Assomption', '2025-08-15'),
('Toussaint', '2025-11-01'),
('Armistice 1918', '2025-11-11'),
('Noël', '2025-12-25');



-- Affichage des tables créées
\dt


-- Administrateur
INSERT INTO administrateur (login, mot_de_passe, nom, prenom, email) VALUES 
('admin', 'admin123', 'Administrateur', 'Principal', 'admin@bibliotheque.com');

-- Adhérents
INSERT INTO adherent (nom, prenom, login, mot_de_passe, email, telephone, adresse, date_naissance, est_etudiant, est_professionnel, est_anonyme, quota_max, quota_actuel) VALUES 
('Dupont', 'Jean', 'jean.dupont', 'password123', 'jean.dupont@email.com', '0123456789', '123 Rue de la Paix, Paris', '1990-05-15', true, false, false, 5, 0),
('Martin', 'Marie', 'marie.martin', 'password456', 'marie.martin@email.com', '0987654321', '456 Avenue des Champs, Lyon', '1985-08-22', false, true, false, 8, 0);

-- Livres
INSERT INTO livre (titre, auteur, isbn, edition, annee_publication, nombre_exemplaires, age_minimum) VALUES 
('Le Petit Prince', 'Antoine de Saint-Exupéry', 9782070612758, 'Gallimard', 1943, 3, 8),
('1984', 'George Orwell', 9782070368228, 'Gallimard', 1949, 2, 16),
('Harry Potter à l''école des sorciers', 'J.K. Rowling', 9782070541270, 'Gallimard', 1997, 4, 10);

-- Descriptions
INSERT INTO description (id_livre, resume, langue, nombre_pages, categorie, editeur) VALUES 
(1, 'Un conte poétique et philosophique sous l''apparence d''un livre pour enfants.', 'Français', 96, 'Conte philosophique', 'Gallimard'),
(2, 'Un roman d''anticipation dystopique décrivant une société totalitaire.', 'Français', 376, 'Science-fiction', 'Gallimard'),
(3, 'Le premier tome des aventures du jeune sorcier Harry Potter.', 'Français', 320, 'Fantasy', 'Gallimard');

-- Exemplaires
INSERT INTO exemplaire (nom_exemplaire, id_livre, etat, localisation, code_barre) VALUES 
('Le Petit Prince - Ex1', 1, 'disponible', 'Rayon A - Section Jeunesse', 'LP001'),
('Le Petit Prince - Ex2', 1, 'disponible', 'Rayon A - Section Jeunesse', 'LP002'),
('Le Petit Prince - Ex3', 1, 'disponible', 'Rayon A - Section Jeunesse', 'LP003'),
('1984 - Ex1', 2, 'disponible', 'Rayon B - Section Littérature', '1984001'),
('1984 - Ex2', 2, 'disponible', 'Rayon B - Section Littérature', '1984002'),
('Harry Potter - Ex1', 3, 'disponible', 'Rayon A - Section Jeunesse', 'HP001'),
('Harry Potter - Ex2', 3, 'disponible', 'Rayon A - Section Jeunesse', 'HP002'),
('Harry Potter - Ex3', 3, 'disponible', 'Rayon A - Section Jeunesse', 'HP003'),
('Harry Potter - Ex4', 3, 'disponible', 'Rayon A - Section Jeunesse', 'HP004');