package com.bibliotheque.service;

import com.bibliotheque.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PretValidationService {
    
    @Autowired
    private AdherentService adherentService;
    
    @Autowired
    private ExemplaireService exemplaireService;
    
    @Autowired
    private LivreService livreService;
    
    @Autowired
    private PenaliteService penaliteService;
    
    @Autowired
    private PretService pretService;
    
    @Autowired
    private ParametrageGeneralService parametrageService;

    /**
     * Valide toutes les règles métier pour la création d'un prêt
     */
    public ValidationResult validerCreationPret(Pret pret) {
        ValidationResult result = new ValidationResult();
        
        // 1. Validation de l'adhérent
        if (pret.getAdherent() == null || pret.getAdherent().getId() == null) {
            result.addError("L'adhérent est obligatoire");
            return result;
        }
        
        Optional<Adherent> adherentOpt = adherentService.findById(pret.getAdherent().getId());
        if (adherentOpt.isEmpty()) {
            result.addError("L'adhérent n'existe pas");
            return result;
        }
        
        Adherent adherent = adherentOpt.get();
        
        // Vérifier si l'adhérent est actif
        if (!adherent.getEstActif()) {
            result.addError("L'adhérent n'est pas actif");
        }
        
        // Vérifier les pénalités actives
        List<Penalite> penalitesActives = penaliteService.findByAdherentAndEstActive(adherent, true);
        if (!penalitesActives.isEmpty()) {
            result.addError("L'adhérent a des pénalités actives");
        }
        
        // Vérifier le quota selon le type d'adhérent
        Integer quotaMax = parametrageService.getQuotaMaxPourAdherent(adherent);
        if (adherent.getQuotaActuel() >= quotaMax) {
            result.addError("L'adhérent a atteint son quota maximum d'emprunts (" + quotaMax + " livres)");
        }
        
        // 2. Validation de l'exemplaire
        if (pret.getExemplaire() == null || pret.getExemplaire().getId() == null) {
            result.addError("L'exemplaire est obligatoire");
            return result;
        }
        
        Optional<Exemplaire> exemplaireOpt = exemplaireService.findById(pret.getExemplaire().getId());
        if (exemplaireOpt.isEmpty()) {
            result.addError("L'exemplaire n'existe pas");
            return result;
        }
        
        Exemplaire exemplaire = exemplaireOpt.get();
        
        // Vérifier si l'exemplaire est disponible
        if (!"disponible".equals(exemplaire.getEtat())) {
            result.addError("L'exemplaire n'est pas disponible (état: " + exemplaire.getEtat() + ")");
        }
        
        // 3. Validation du livre
        if (exemplaire.getLivre() == null) {
            result.addError("L'exemplaire n'est associé à aucun livre");
            return result;
        }
        
        Optional<Livre> livreOpt = livreService.findById(exemplaire.getLivre().getId());
        if (livreOpt.isEmpty()) {
            result.addError("Le livre associé à l'exemplaire n'existe pas");
            return result;
        }
        
        // 4. Validation du type de prêt
        if (pret.getTypePret() == null || pret.getTypePret().trim().isEmpty()) {
            pret.setTypePret("domicile"); // Valeur par défaut
        } else if (!"domicile".equals(pret.getTypePret()) && !"sur_lieu".equals(pret.getTypePret())) {
            result.addError("Le type de prêt doit être 'domicile' ou 'sur_lieu'");
        }
        
        // 5. Validation des dates
        if (pret.getDateDebut() == null) {
            pret.setDateDebut(LocalDate.now());
        }
        
        if (pret.getDateFinPrevue() == null) {
            // Calculer automatiquement la date de fin selon le type d'adhérent
            pret.setDateFinPrevue(parametrageService.calculerDateFinPrevue(adherent, pret.getDateDebut()));
        } else if (pret.getDateFinPrevue().isBefore(pret.getDateDebut()) || pret.getDateFinPrevue().isEqual(pret.getDateDebut())) {
            result.addError("La date de fin prévue doit être postérieure à la date de début");
        }
        
        return result;
    }
    
    /**
     * Valide les règles pour le retour d'un livre
     */
    public ValidationResult validerRetourPret(Pret pret) {
        ValidationResult result = new ValidationResult();
        
        if (pret == null || pret.getId() == null) {
            result.addError("Le prêt est obligatoire");
            return result;
        }
        
        Optional<Pret> pretOpt = pretService.findById(pret.getId());
        if (pretOpt.isEmpty()) {
            result.addError("Le prêt n'existe pas");
            return result;
        }
        
        Pret pretExistant = pretOpt.get();
        
        // Vérifier que le prêt n'est pas déjà retourné
        if (pretExistant.getDateRenduReelle() != null) {
            result.addError("Ce prêt a déjà été retourné");
        }
        
        return result;
    }
    
    /**
     * Valide les règles pour le prolongement d'un prêt
     */
    public ValidationResult validerProlongementPret(ProlongementPret prolongement) {
        ValidationResult result = new ValidationResult();
        
        if (prolongement.getPret() == null || prolongement.getPret().getId() == null) {
            result.addError("Le prêt est obligatoire");
            return result;
        }
        
        Optional<Pret> pretOpt = pretService.findById(prolongement.getPret().getId());
        if (pretOpt.isEmpty()) {
            result.addError("Le prêt n'existe pas");
            return result;
        }
        
        Pret pret = pretOpt.get();
        
        // Vérifier que le prêt n'a pas déjà été prolongé le nombre maximum de fois
        Integer nombreProlongationsMax = parametrageService.getNombreProlongationsMax();
        if (pret.getEstProlonge()) {
            result.addError("Ce prêt a déjà été prolongé le nombre maximum de fois autorisé (" + nombreProlongationsMax + ")");
        }
        
        // Vérifier que le prêt n'est pas encore retourné
        if (pret.getDateRenduReelle() != null) {
            result.addError("Impossible de prolonger un prêt déjà retourné");
        }
        
        // Vérifier les dates du prolongement
        if (prolongement.getDateDemande() == null) {
            prolongement.setDateDemande(LocalDate.now());
        }
        
        if (prolongement.getNouvelleDateRendu() == null) {
            // Calculer automatiquement la nouvelle date selon la durée de prolongation
            Integer dureeProlongation = parametrageService.getDureeProlongation();
            prolongement.setNouvelleDateRendu(pret.getDateFinPrevue().plusDays(dureeProlongation));
        } else if (prolongement.getNouvelleDateRendu().isBefore(prolongement.getDateDemande()) || 
                   prolongement.getNouvelleDateRendu().isEqual(prolongement.getDateDemande())) {
            result.addError("La nouvelle date de rendu doit être postérieure à la date de demande");
        }
        
        return result;
    }
    
    /**
     * Classe pour encapsuler le résultat de validation
     */
    public static class ValidationResult {
        private boolean valid = true;
        private List<String> errors = new java.util.ArrayList<>();
        
        public boolean isValid() {
            return valid && errors.isEmpty();
        }
        
        public void addError(String error) {
            this.valid = false;
            this.errors.add(error);
        }
        
        public List<String> getErrors() {
            return errors;
        }
        
        public String getFirstError() {
            return errors.isEmpty() ? "" : errors.get(0);
        }
        
        public String getAllErrorsAsString() {
            return String.join("; ", errors);
        }
    }
} 