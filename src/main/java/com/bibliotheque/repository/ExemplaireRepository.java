package com.bibliotheque.repository;

import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    List<Exemplaire> findByLivre(Livre livre);
    
    /**
     * Compte le nombre d'exemplaires par état
     */
    int countByEtat(String etat);

    List<Exemplaire> findByLivreAndEtat(Livre livre, String etat);
} 