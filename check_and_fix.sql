-- Script pour vérifier et corriger les IDs

-- 1. Vérifier les IDs des adhérents
SELECT id_adherent, nom, prenom, login FROM adherent;

-- 2. Vérifier les IDs des livres
SELECT id_livre, titre, auteur FROM livre;

-- 3. Insérer les inscriptions avec les bons IDs
-- (Remplacer les IDs par ceux obtenus ci-dessus)

-- Exemple si les adhérents ont les IDs 1 et 2 :
INSERT INTO inscription (id_adherent, date_inscription, statut_inscription) 
VALUES 
(1, '2024-01-15', 'actif'),
(2, '2024-02-01', 'actif');

-- 4. Vérification finale
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