package com.bibliotheque.repository;

import com.bibliotheque.model.TableauDeBord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TableauDeBordRepository extends JpaRepository<TableauDeBord, Integer> {
    
    /**
     * Trouve les statistiques pour une date donnée
     */
    Optional<TableauDeBord> findByDateStat(LocalDate dateStat);
    
    /**
     * Trouve les statistiques entre deux dates, triées par date décroissante
     */
    List<TableauDeBord> findByDateStatBetweenOrderByDateStatDesc(LocalDate dateDebut, LocalDate dateFin);
    
    /**
     * Trouve toutes les statistiques, triées par date décroissante
     */
    @Query("SELECT t FROM TableauDeBord t ORDER BY t.dateStat DESC")
    List<TableauDeBord> findAllByOrderByDateStatDesc();
    
    /**
     * Supprime les statistiques antérieures à une date
     */
    void deleteByDateStatBefore(LocalDate date);
    
    /**
     * Compte le nombre de statistiques pour une date donnée
     */
    long countByDateStat(LocalDate dateStat);
} 