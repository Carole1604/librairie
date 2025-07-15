package com.bibliotheque.service;

import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Adherent;
import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.model.Penalite;
import com.bibliotheque.repository.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PretService {
    private static final Logger logger = LoggerFactory.getLogger(PretService.class);
    
    @Autowired
    private PretRepository pretRepository;
    
    @Autowired
    private AdherentService adherentService;
    
    @Autowired
    private ExemplaireService exemplaireService;
    
    @Autowired
    private PenaliteService penaliteService;
    
    @Autowired
    private TableauDeBordService tableauDeBordService;

    public List<Pret> findAll() {
        return pretRepository.findAll();
    }

    public Optional<Pret> findById(Integer id) {
        return pretRepository.findById(id);
    }

    @Transactional
    public Pret save(Pret pret) {
        try {
            return pretRepository.save(pret);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du prêt: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la sauvegarde du prêt: " + e.getMessage(), e);
        }
    }

    public void deleteById(Integer id) {
        pretRepository.deleteById(id);
    }
    
    public List<Pret> findByAdherentId(Integer adherentId) {
        return pretRepository.findByAdherentId(adherentId);
    }
    
    public List<Pret> findByAdherent(Adherent adherent) {
        return pretRepository.findByAdherent(adherent);
    }
    
    /**
     * Crée un prêt avec mise à jour automatique du quota et de l'état de l'exemplaire
     */
    @Transactional
    public Pret creerPret(Pret pret) {
        try {
            logger.info("Début de création du prêt pour l'adhérent: {}", 
                       pret.getAdherent() != null ? pret.getAdherent().getId() : "null");
            
            // Validation préalable
            if (pret.getAdherent() == null) {
                throw new IllegalArgumentException("L'adhérent est obligatoire");
            }
            if (pret.getExemplaire() == null) {
                throw new IllegalArgumentException("L'exemplaire est obligatoire");
            }
            
            // Récupérer les entités fraîches depuis la base
            Optional<Adherent> adherentOpt = adherentService.findById(pret.getAdherent().getId());
            if (adherentOpt.isEmpty()) {
                throw new IllegalArgumentException("Adhérent non trouvé avec l'ID: " + pret.getAdherent().getId());
            }
            Adherent adherent = adherentOpt.get();
            
            Optional<Exemplaire> exemplaireOpt = exemplaireService.findById(pret.getExemplaire().getId());
            if (exemplaireOpt.isEmpty()) {
                throw new IllegalArgumentException("Exemplaire non trouvé avec l'ID: " + pret.getExemplaire().getId());
            }
            Exemplaire exemplaire = exemplaireOpt.get();
            
            // Vérifications métier
            if (!adherent.getEstActif()) {
                throw new IllegalArgumentException("L'adhérent n'est pas actif");
            }
            
            if (!"disponible".equals(exemplaire.getEtat())) {
                throw new IllegalArgumentException("L'exemplaire n'est pas disponible (état: " + exemplaire.getEtat() + ")");
            }
            
            // Sauvegarder le prêt
            pret.setAdherent(adherent);
            pret.setExemplaire(exemplaire);
            Pret pretSauvegarde = save(pret);
            
            logger.info("Prêt créé avec succès, ID: {}", pretSauvegarde.getId());
            
            // Mettre à jour le quota de l'adhérent
            try {
                adherent.setQuotaActuel(adherent.getQuotaActuel() + 1);
                adherentService.save(adherent);
                logger.info("Quota de l'adhérent mis à jour: {}", adherent.getQuotaActuel());
            } catch (Exception e) {
                logger.error("Erreur lors de la mise à jour du quota: {}", e.getMessage(), e);
                throw new RuntimeException("Erreur lors de la mise à jour du quota", e);
            }
            
            // Mettre à jour l'état de l'exemplaire
            try {
                exemplaire.setEtat("emprunte");
                exemplaireService.save(exemplaire);
                logger.info("État de l'exemplaire mis à jour: {}", exemplaire.getEtat());
            } catch (Exception e) {
                logger.error("Erreur lors de la mise à jour de l'exemplaire: {}", e.getMessage(), e);
                throw new RuntimeException("Erreur lors de la mise à jour de l'exemplaire", e);
            }
            
            // Mettre à jour les statistiques
            try {
                tableauDeBordService.mettreAJourStatistiques(LocalDate.now());
                logger.info("Statistiques mises à jour");
            } catch (Exception e) {
                logger.warn("Erreur lors de la mise à jour des statistiques: {}", e.getMessage());
                // Ne pas faire échouer le prêt pour une erreur de statistiques
            }
            
            return pretSauvegarde;
            
        } catch (Exception e) {
            logger.error("Erreur lors de la création du prêt: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la création du prêt: " + e.getMessage(), e);
        }
    }
    
    /**
     * Enregistre le retour d'un livre avec création automatique de pénalité si retard
     */
    @Transactional
    public Pret retournerLivre(Integer pretId) {
        try {
            logger.info("Début du retour du prêt ID: {}", pretId);
            
            Optional<Pret> pretOpt = findById(pretId);
            if (pretOpt.isEmpty()) {
                throw new IllegalArgumentException("Prêt non trouvé avec l'ID: " + pretId);
            }
            
            Pret pret = pretOpt.get();
            pret.setDateRenduReelle(LocalDate.now());
            
            // Récupérer les entités fraîches
            Adherent adherent = pret.getAdherent();
            Exemplaire exemplaire = pret.getExemplaire();
            
            // Mettre à jour le quota de l'adhérent
            try {
                adherent.setQuotaActuel(Math.max(0, adherent.getQuotaActuel() - 1));
                adherentService.save(adherent);
                logger.info("Quota de l'adhérent mis à jour: {}", adherent.getQuotaActuel());
            } catch (Exception e) {
                logger.error("Erreur lors de la mise à jour du quota: {}", e.getMessage(), e);
                throw new RuntimeException("Erreur lors de la mise à jour du quota", e);
            }
            
            // Mettre à jour l'état de l'exemplaire
            try {
                exemplaire.setEtat("disponible");
                exemplaireService.save(exemplaire);
                logger.info("État de l'exemplaire mis à jour: {}", exemplaire.getEtat());
            } catch (Exception e) {
                logger.error("Erreur lors de la mise à jour de l'exemplaire: {}", e.getMessage(), e);
                throw new RuntimeException("Erreur lors de la mise à jour de l'exemplaire", e);
            }
            
            // Sauvegarder le prêt mis à jour
            Pret pretSauvegarde = save(pret);
            logger.info("Prêt retourné avec succès");
            
            // Créer automatiquement une pénalité si il y a un retard
            try {
                Penalite penalite = penaliteService.creerPenaliteAutomatique(pretSauvegarde);
                if (penalite != null) {
                    logger.info("Pénalité créée automatiquement pour le prêt #{} - {} jours de retard", 
                               pretId, penalite.getNombreJoursRetard());
                }
            } catch (Exception e) {
                logger.warn("Erreur lors de la création de la pénalité: {}", e.getMessage());
                // Ne pas faire échouer le retour pour une erreur de pénalité
            }
            
            // Mettre à jour les statistiques
            try {
                tableauDeBordService.mettreAJourStatistiques(LocalDate.now());
                logger.info("Statistiques mises à jour");
            } catch (Exception e) {
                logger.warn("Erreur lors de la mise à jour des statistiques: {}", e.getMessage());
                // Ne pas faire échouer le retour pour une erreur de statistiques
            }
            
            return pretSauvegarde;
            
        } catch (Exception e) {
            logger.error("Erreur lors du retour du prêt: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors du retour du prêt: " + e.getMessage(), e);
        }
    }
    
    /**
     * Trouve tous les prêts retournés
     */
    public List<Pret> findPretsRetournes() {
        return pretRepository.findByDateRenduReelleIsNotNull();
    }
} 