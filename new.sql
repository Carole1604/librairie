CREATE TABLE administrateur (
    id_administrateur SERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

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
    editeur VARCHAR(150),
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
    editeur VARCHAR(150),
    mots_cles TEXT
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
    id_statut INT DEFAULT 1 REFERENCES statut(id_statut),
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