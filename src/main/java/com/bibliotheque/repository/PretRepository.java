package com.bibliotheque.repository;

import com.bibliotheque.model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    int countByDateDebut(LocalDate dateDebut);
    int countByDateRenduReelle(LocalDate dateRenduReelle);
    
    // Méthodes pour filtrer par adhérent
    List<Pret> findByAdherentId(Integer adherentId);
    List<Pret> findByAdherent(com.bibliotheque.model.Adherent adherent);
    
    // Méthode pour trouver les prêts retournés
    List<Pret> findByDateRenduReelleIsNotNull();
} 