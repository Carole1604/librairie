package com.bibliotheque.controller;

import com.bibliotheque.model.ProlongementPret;
import com.bibliotheque.model.Statut;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.service.ProlongementPretService;
import com.bibliotheque.service.PretValidationService;
import com.bibliotheque.service.PretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prolongements")
public class ProlongementPretController {
    @Autowired
    private ProlongementPretService prolongementPretService;
    
    @Autowired
    private PretValidationService validationService;
    
    @Autowired
    private PretService pretService;

    @Autowired
    private StatutRepository statutRepository;

    @GetMapping("")
    public String index() {
        return "redirect:/prolongements/liste";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/prolongements/liste";
    }

    @GetMapping("/liste")
    public String listeProlongements(Model model) {
        model.addAttribute("prolongements", prolongementPretService.findAll());
        return "prolongements/liste";
    }

    @GetMapping("/demande")
    public String demandeForm(@RequestParam(required = false) Integer pretId, Model model) {
        ProlongementPret prolongement = new ProlongementPret();
        
        if (pretId != null) {
            // Charger les détails du prêt si un ID est fourni
            pretService.findById(pretId).ifPresent(pret -> {
                prolongement.setPret(pret);
                model.addAttribute("pret", pret);
            });
        }
        
        model.addAttribute("prolongement", prolongement);
        return "prolongements/demande";
    }

    @PostMapping("/demande")
    public String demanderProlongement(@ModelAttribute ProlongementPret prolongementPret, Model model) {
        // Validation des règles métier
        PretValidationService.ValidationResult validation = validationService.validerProlongementPret(prolongementPret);
        
        if (!validation.isValid()) {
            model.addAttribute("erreur", validation.getAllErrorsAsString());
            model.addAttribute("prolongement", prolongementPret);
            return "prolongements/erreur";
        }
        
        try {
            // Forcer le statut 'en_cours' (id = 1)
            Statut statutEnCours = statutRepository.findById(1).orElse(null);
            prolongementPret.setStatut(statutEnCours);
            prolongementPretService.save(prolongementPret);
            return "redirect:/prolongements/liste";
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la demande de prolongement: " + e.getMessage());
            model.addAttribute("prolongement", prolongementPret);
            return "prolongements/erreur";
        }
    }

    @GetMapping("/validation/{id}")
    public String validationForm(@PathVariable Integer id, Model model) {
        ProlongementPret prolongement = prolongementPretService.findById(id).orElse(null);
        model.addAttribute("prolongement", prolongement);
        return "prolongements/validation";
    }

    @PostMapping("/validation")
    public String validerProlongement(@ModelAttribute ProlongementPret prolongementPret, Model model) {
        try {
            // Récupérer le prolongement existant pour préserver les champs obligatoires
            ProlongementPret existingProlongement = prolongementPretService.findById(prolongementPret.getId()).orElse(null);
            if (existingProlongement != null) {
                // Mettre à jour seulement les champs de validation
                existingProlongement.setStatut(prolongementPret.getStatut());
                existingProlongement.setMotifRefus(prolongementPret.getMotifRefus());
                existingProlongement.setDateValidation(java.time.LocalDate.now());
                
                // Si validé, mettre à jour le prêt
                if ("validé".equals(prolongementPret.getStatut().getNomStatut())) {
                    existingProlongement.getPret().setEstProlonge(true);
                    existingProlongement.getPret().setDateFinPrevue(prolongementPret.getNouvelleDateRendu());
                }
                
                prolongementPretService.save(existingProlongement);
            }
            return "redirect:/prolongements/liste";
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la validation: " + e.getMessage());
            model.addAttribute("prolongement", prolongementPret);
            return "prolongements/erreur";
        }
    }
} 