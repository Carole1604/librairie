package com.bibliotheque.controller;

import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Adherent;
import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.service.PretService;
import com.bibliotheque.service.AdherentService;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.service.PretValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.service.ReservationService;

@Controller
@RequestMapping("/prets")
public class PretController {
    private static final Logger logger = LoggerFactory.getLogger(PretController.class);
    
    @Autowired
    private PretService pretService;
    @Autowired
    private AdherentService adherentService;
    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private PretValidationService validationService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("")
    public String index() {
        return "redirect:/prets/liste";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/prets/liste";
    }

    @GetMapping("/liste")
    public String listePrets(Model model) {
        try {
            model.addAttribute("prets", pretService.findAll());
            return "prets/liste";
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des prêts: {}", e.getMessage(), e);
            model.addAttribute("erreur", "Erreur lors de la récupération des prêts: " + e.getMessage());
            return "prets/erreur";
        }
    }

    @GetMapping("/creation")
    public String creationForm(Model model) {
        try {
            model.addAttribute("pret", new Pret());
            model.addAttribute("adherents", adherentService.findAll());
            model.addAttribute("exemplaires", exemplaireService.findAll());
            return "prets/creation";
        } catch (Exception e) {
            logger.error("Erreur lors du chargement du formulaire de création: {}", e.getMessage(), e);
            model.addAttribute("erreur", "Erreur lors du chargement du formulaire: " + e.getMessage());
            return "prets/erreur";
        }
    }

    @PostMapping("/creation")
    public String creerPret(@ModelAttribute Pret pret, Model model) {
        logger.info("Tentative de création d'un prêt pour l'adhérent: {}", 
                   pret.getAdherent() != null ? pret.getAdherent().getId() : "null");
        
        try {
            // Validation des données d'entrée
            if (pret.getAdherent() == null || pret.getAdherent().getId() == null) {
                throw new IllegalArgumentException("L'adhérent est obligatoire");
            }
            if (pret.getExemplaire() == null || pret.getExemplaire().getId() == null) {
                throw new IllegalArgumentException("L'exemplaire est obligatoire");
            }
            
            // Récupérer les entités complètes
            Optional<Adherent> adherentOpt = adherentService.findById(pret.getAdherent().getId());
            if (adherentOpt.isEmpty()) {
                throw new IllegalArgumentException("Adhérent non trouvé");
            }
            pret.setAdherent(adherentOpt.get());
            
            Optional<Exemplaire> exemplaireOpt = exemplaireService.findById(pret.getExemplaire().getId());
            if (exemplaireOpt.isEmpty()) {
                throw new IllegalArgumentException("Exemplaire non trouvé");
            }
            pret.setExemplaire(exemplaireOpt.get());
            
            // Validation des règles métier
            PretValidationService.ValidationResult validation = validationService.validerCreationPret(pret);
            
            if (!validation.isValid()) {
                logger.warn("Validation échouée pour le prêt: {}", validation.getAllErrorsAsString());
                model.addAttribute("erreur", validation.getAllErrorsAsString());
                model.addAttribute("pret", pret);
                model.addAttribute("adherents", adherentService.findAll());
                model.addAttribute("exemplaires", exemplaireService.findAll());
                return "prets/erreur";
            }
            
            // Créer le prêt
            Pret pretCree = pretService.creerPret(pret);
            logger.info("Prêt créé avec succès, ID: {}", pretCree.getId());
            
            return "redirect:/prets/liste?success=Prêt créé avec succès";
            
        } catch (IllegalArgumentException e) {
            logger.warn("Erreur de validation lors de la création du prêt: {}", e.getMessage());
            model.addAttribute("erreur", e.getMessage());
            model.addAttribute("pret", pret);
            model.addAttribute("adherents", adherentService.findAll());
            model.addAttribute("exemplaires", exemplaireService.findAll());
            return "prets/erreur";
            
        } catch (Exception e) {
            logger.error("Erreur lors de la création du prêt: {}", e.getMessage(), e);
            model.addAttribute("erreur", "Erreur lors de la création du prêt: " + e.getMessage());
            model.addAttribute("pret", pret);
            model.addAttribute("adherents", adherentService.findAll());
            model.addAttribute("exemplaires", exemplaireService.findAll());
            return "prets/erreur";
        }
    }

    @GetMapping("/retour/{id}")
    public String retourForm(@PathVariable Integer id, Model model) {
        try {
            Optional<Pret> pretOpt = pretService.findById(id);
            if (pretOpt.isEmpty()) {
                model.addAttribute("erreur", "Prêt non trouvé avec l'ID: " + id);
                return "prets/erreur";
            }
            
            Pret pret = pretOpt.get();
            model.addAttribute("pret", pret);
            return "prets/retour";
            
        } catch (Exception e) {
            logger.error("Erreur lors du chargement du formulaire de retour: {}", e.getMessage(), e);
            model.addAttribute("erreur", "Erreur lors du chargement du formulaire de retour: " + e.getMessage());
            return "prets/erreur";
        }
    }

    @PostMapping("/retour")
    public String enregistrerRetour(@RequestParam Integer id, Model model) {
        logger.info("Tentative de retour du prêt ID: {}", id);
        
        try {
            Optional<Pret> pretOpt = pretService.findById(id);
            if (pretOpt.isEmpty()) {
                throw new IllegalArgumentException("Prêt non trouvé avec l'ID: " + id);
            }
            
            Pret pret = pretOpt.get();
            
            // Vérifier que le prêt n'est pas déjà retourné
            if (pret.getDateRenduReelle() != null) {
                throw new IllegalArgumentException("Ce prêt a déjà été retourné");
            }
            
            // Effectuer le retour
            Pret pretRetourne = pretService.retournerLivre(id);
            logger.info("Prêt retourné avec succès, ID: {}", pretRetourne.getId());
            
            return "redirect:/prets/liste?success=Livre retourné avec succès";
            
        } catch (IllegalArgumentException e) {
            logger.warn("Erreur de validation lors du retour: {}", e.getMessage());
            model.addAttribute("erreur", e.getMessage());
            return "prets/erreur";
            
        } catch (Exception e) {
            logger.error("Erreur lors du retour du prêt: {}", e.getMessage(), e);
            model.addAttribute("erreur", "Erreur lors du retour du prêt: " + e.getMessage());
            return "prets/erreur";
        }
    }

    @GetMapping("/creation/reservation/{reservationId}")
    public String creationPretDepuisReservation(@PathVariable Integer reservationId, Model model) {
        // Charger la réservation
        Reservation reservation = reservationService.findById(reservationId).orElse(null);
        if (reservation == null || reservation.getStatut() == null || !"valide".equals(reservation.getStatut().getNomStatut())) {
            model.addAttribute("erreur", "Réservation non trouvée ou non validée.");
            return "prets/erreur";
        }
        // Préparer le prêt pré-rempli
        Pret pret = new Pret();
        pret.setAdherent(reservation.getAdherent());
        pret.setReservation(reservation);
        // Proposer les exemplaires disponibles du livre réservé
        java.util.List<Exemplaire> exemplairesDisponibles = exemplaireService.findDisponiblesByLivre(reservation.getLivre());
        model.addAttribute("pret", pret);
        model.addAttribute("adherents", java.util.List.of(reservation.getAdherent()));
        model.addAttribute("exemplaires", exemplairesDisponibles);
        return "prets/creation";
    }

    @PostMapping("/creation/reservation")
    public String creerPretDepuisReservation(@ModelAttribute Pret pret, Model model) {
        // Même logique que la création classique
        return creerPret(pret, model);
    }
} 