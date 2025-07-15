package com.bibliotheque.service;

import com.bibliotheque.model.TableauDeBord;
import com.bibliotheque.repository.TableauDeBordRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.PenaliteRepository;
import com.bibliotheque.repository.AdherentRepository;
import com.bibliotheque.repository.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TableauDeBordService {
    @Autowired
    private TableauDeBordRepository tableauDeBordRepository;
    @Autowired
    private PretRepository pretRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PenaliteRepository penaliteRepository;
    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private ExemplaireRepository exemplaireRepository;

    /**
     * Récupère les statistiques pour une date donnée
     */
    public TableauDeBord getStatistiquesPourDate(LocalDate date) {
        // Vérifier si des statistiques existent déjà pour cette date
        Optional<TableauDeBord> existingStats = findByDate(date);
        if (existingStats.isPresent()) {
            return existingStats.get();
        }
        
        // Calculer les nouvelles statistiques
        TableauDeBord stats = calculerStatistiques(date);
        
        // Sauvegarder les statistiques calculées
        return save(stats);
    }

    /**
     * Calcule les statistiques pour une date donnée
     */
    public TableauDeBord calculerStatistiques(LocalDate date) {
        TableauDeBord stats = new TableauDeBord();
        stats.setDateStat(date);
        
        // Nombre de prêts pour cette date
        stats.setNombrePrets(pretRepository.countByDateDebut(date));
        
        // Nombre de retours pour cette date
        stats.setNombreRetours(pretRepository.countByDateRenduReelle(date));
        
        // Nombre de réservations pour cette date
        stats.setNombreReservations(reservationRepository.countByDateReservation(date));
        
        // Nombre de pénalités créées pour cette date
        stats.setNombrePenalites(penaliteRepository.countByDateDebut(date));
        
        // Nombre d'adhérents actifs (total, pas seulement pour cette date)
        stats.setNombreAdherentsActifs(adherentRepository.countByEstActifTrue());
        
        // Nombre de livres disponibles (exemplaires avec état 'disponible')
        stats.setNombreLivresDisponibles(exemplaireRepository.countByEtat("disponible"));
        
        return stats;
    }

    /**
     * Met à jour automatiquement les statistiques pour une date donnée
     */
    @Transactional
    public void mettreAJourStatistiques(LocalDate date) {
        TableauDeBord stats = calculerStatistiques(date);
        
        // Vérifier si des statistiques existent déjà
        Optional<TableauDeBord> existingStats = findByDate(date);
        if (existingStats.isPresent()) {
            TableauDeBord existing = existingStats.get();
            existing.setNombrePrets(stats.getNombrePrets());
            existing.setNombreRetours(stats.getNombreRetours());
            existing.setNombreReservations(stats.getNombreReservations());
            existing.setNombrePenalites(stats.getNombrePenalites());
            existing.setNombreAdherentsActifs(stats.getNombreAdherentsActifs());
            existing.setNombreLivresDisponibles(stats.getNombreLivresDisponibles());
            save(existing);
        } else {
            save(stats);
        }
    }

    /**
     * Met à jour automatiquement les statistiques pour aujourd'hui
     */
    @Transactional
    public void mettreAJourStatistiquesAujourdhui() {
        mettreAJourStatistiques(LocalDate.now());
    }

    /**
     * Récupère les statistiques pour une date donnée
     */
    public Optional<TableauDeBord> findByDate(LocalDate date) {
        return tableauDeBordRepository.findByDateStat(date);
    }

    /**
     * Sauvegarde les statistiques
     */
    @Transactional
    public TableauDeBord save(TableauDeBord tableau) {
        return tableauDeBordRepository.save(tableau);
    }

    /**
     * Récupère les statistiques des 7 derniers jours
     */
    public List<TableauDeBord> getStatistiques7DerniersJours() {
        LocalDate aujourdhui = LocalDate.now();
        LocalDate ilYA7Jours = aujourdhui.minusDays(6);
        return tableauDeBordRepository.findByDateStatBetweenOrderByDateStatDesc(ilYA7Jours, aujourdhui);
    }

    /**
     * Récupère les statistiques du mois en cours
     */
    public List<TableauDeBord> getStatistiquesMoisEnCours() {
        LocalDate aujourdhui = LocalDate.now();
        LocalDate debutMois = aujourdhui.withDayOfMonth(1);
        return tableauDeBordRepository.findByDateStatBetweenOrderByDateStatDesc(debutMois, aujourdhui);
    }

    /**
     * Récupère les statistiques globales (toutes les dates)
     */
    public List<TableauDeBord> getAllStatistiques() {
        return tableauDeBordRepository.findAllByOrderByDateStatDesc();
    }

    /**
     * Supprime les anciennes statistiques (plus de 1 an)
     */
    @Transactional
    public void nettoyerAnciennesStatistiques() {
        LocalDate ilYA1An = LocalDate.now().minusYears(1);
        tableauDeBordRepository.deleteByDateStatBefore(ilYA1An);
    }
} 