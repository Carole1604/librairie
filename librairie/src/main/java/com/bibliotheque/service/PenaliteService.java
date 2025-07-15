package com.bibliotheque.service;

import com.bibliotheque.model.Penalite;
import com.bibliotheque.model.Adherent;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.JourFerie;
import com.bibliotheque.repository.PenaliteRepository;
import com.bibliotheque.repository.JourFerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PenaliteService {
    @Autowired
    private PenaliteRepository penaliteRepository;
    
    @Autowired
    private JourFerieRepository jourFerieRepository;

    public List<Penalite> findAll() {
        return penaliteRepository.findAll();
    }

    public Optional<Penalite> findById(Integer id) {
        return penaliteRepository.findById(id);
    }

    public Penalite save(Penalite penalite) {
        return penaliteRepository.save(penalite);
    }
    
    public List<Penalite> findByAdherentAndEstActive(Adherent adherent, Boolean estActive) {
        return penaliteRepository.findByAdherentAndEstActive(adherent, estActive);
    }
    
    public List<Penalite> findByAdherent(Adherent adherent) {
        return penaliteRepository.findByAdherent(adherent);
    }
    
    /**
     * Calcule le nombre de jours de retard en excluant les jours fériés et week-ends
     */
    public int calculerJoursRetard(LocalDate dateFinPrevue, LocalDate dateRetourReelle) {
        if (dateRetourReelle.isBefore(dateFinPrevue) || dateRetourReelle.isEqual(dateFinPrevue)) {
            return 0; // Pas de retard
        }
        
        int joursRetard = 0;
        LocalDate date = dateFinPrevue.plusDays(1); // Commencer le jour suivant
        
        while (!date.isAfter(dateRetourReelle)) {
            // Vérifier si c'est un week-end
            DayOfWeek jourSemaine = date.getDayOfWeek();
            if (jourSemaine != DayOfWeek.SATURDAY && jourSemaine != DayOfWeek.SUNDAY) {
                // Vérifier si c'est un jour férié
                if (!estJourFerie(date)) {
                    joursRetard++;
                }
            }
            date = date.plusDays(1);
        }
        
        return joursRetard;
    }
    
    /**
     * Vérifie si une date est un jour férié
     */
    private boolean estJourFerie(LocalDate date) {
        return jourFerieRepository.findByDateFerie(date).isPresent();
    }
    
    /**
     * Calcule la date de fin de la pénalité (durée égale aux jours de retard)
     */
    public LocalDate calculerDateFinPenalite(LocalDate dateDebut, int joursRetard) {
        if (joursRetard <= 0) {
            return dateDebut;
        }
        
        LocalDate dateFin = dateDebut;
        int joursAjoutes = 0;
        
        while (joursAjoutes < joursRetard) {
            dateFin = dateFin.plusDays(1);
            DayOfWeek jourSemaine = dateFin.getDayOfWeek();
            
            // Ne compter que les jours ouvrés (pas les week-ends ni jours fériés)
            if (jourSemaine != DayOfWeek.SATURDAY && jourSemaine != DayOfWeek.SUNDAY && !estJourFerie(dateFin)) {
                joursAjoutes++;
            }
        }
        
        return dateFin;
    }
    
    /**
     * Crée automatiquement une pénalité pour un prêt en retard
     */
    @Transactional
    public Penalite creerPenaliteAutomatique(Pret pret) {
        if (pret.getDateRenduReelle() == null || pret.getDateFinPrevue() == null) {
            throw new IllegalArgumentException("Les dates de retour et de fin prévue sont obligatoires");
        }
        
        int joursRetard = calculerJoursRetard(pret.getDateFinPrevue(), pret.getDateRenduReelle());
        
        if (joursRetard <= 0) {
            return null; // Pas de pénalité si pas de retard
        }
        
        Penalite penalite = new Penalite();
        penalite.setAdherent(pret.getAdherent());
        penalite.setPret(pret);
        penalite.setDateDebut(pret.getDateRenduReelle());
        penalite.setDateFin(calculerDateFinPenalite(pret.getDateRenduReelle(), joursRetard));
        penalite.setMotif("Retard de " + joursRetard + " jour(s) sur le prêt #" + pret.getId());
        penalite.setNombreJoursRetard(joursRetard);
        penalite.setEstActive(true);
        penalite.setDateCreation(LocalDateTime.now());
        
        return save(penalite);
    }
    
    /**
     * Vérifie si un adhérent a des pénalités actives
     */
    public boolean aPenalitesActives(Adherent adherent) {
        List<Penalite> penalitesActives = findByAdherentAndEstActive(adherent, true);
        return !penalitesActives.isEmpty();
    }
    
    /**
     * Désactive les pénalités expirées
     */
    @Transactional
    public void desactiverPenalitesExpirees() {
        List<Penalite> penalitesActives = penaliteRepository.findByEstActiveTrue();
        LocalDate aujourdhui = LocalDate.now();
        
        for (Penalite penalite : penalitesActives) {
            if (penalite.getDateFin() != null && penalite.getDateFin().isBefore(aujourdhui)) {
                penalite.setEstActive(false);
                save(penalite);
            }
        }
    }
} 