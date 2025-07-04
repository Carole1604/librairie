-- Script pour ajouter la table administrateur à la base de données existante

-- Création de la table administrateur
CREATE TABLE IF NOT EXISTS administrateur (
    id_administrateur INT SERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertion d'un administrateur de test
INSERT INTO administrateur (login, mot_de_passe, nom, prenom, email) 
VALUES ('admin', 'admin123', 'Administrateur', 'Principal', 'admin@bibliotheque.com')
ON CONFLICT (login) DO NOTHING;

-- Vérification que la table a été créée
SELECT * FROM administrateur; 

