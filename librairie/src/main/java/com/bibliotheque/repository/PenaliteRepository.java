package com.bibliotheque.repository;

import com.bibliotheque.model.Penalite;
import com.bibliotheque.model.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    int countByDateDebut(LocalDate dateDebut);
    
    List<Penalite> findByAdherentAndEstActive(Adherent adherent, Boolean estActive);
    
    List<Penalite> findByAdherent(Adherent adherent);
    
    List<Penalite> findByEstActiveTrue();
} 