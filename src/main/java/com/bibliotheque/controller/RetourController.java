package com.bibliotheque.controller;

import com.bibliotheque.model.Pret;
import com.bibliotheque.service.PretService;
import com.bibliotheque.service.PretValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/retours")
public class RetourController {
    
    @Autowired
    private PretService pretService;
    
    @Autowired
    private PretValidationService validationService;

    @GetMapping("")
    public String index() {
        return "redirect:/retours/liste";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/retours/liste";
    }

    @GetMapping("/liste")
    public String listeRetours(Model model) {
        // Afficher tous les prêts retournés (avec date_rendu_reelle non null)
        model.addAttribute("prets", pretService.findPretsRetournes());
        return "retours/liste";
    }

    @GetMapping("/retour/{pretId}")
    public String retourForm(@PathVariable Integer pretId, Model model) {
        Pret pret = pretService.findById(pretId).orElse(null);
        if (pret == null) {
            model.addAttribute("erreur", "Prêt non trouvé");
            return "retours/erreur";
        }
        
        model.addAttribute("pret", pret);
        return "retours/retour";
    }

    @PostMapping("/retour")
    public String enregistrerRetour(@RequestParam Integer pretId, Model model) {
        Pret pret = pretService.findById(pretId).orElse(null);
        if (pret == null) {
            model.addAttribute("erreur", "Prêt non trouvé");
            return "retours/erreur";
        }
        
        // Validation du retour
        PretValidationService.ValidationResult validation = validationService.validerRetourPret(pret);
        if (!validation.isValid()) {
            model.addAttribute("erreur", validation.getAllErrorsAsString());
            model.addAttribute("pret", pret);
            return "retours/erreur";
        }
        
        try {
            pretService.retournerLivre(pretId);
            return "redirect:/retours/liste";
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors du retour: " + e.getMessage());
            model.addAttribute("pret", pret);
            return "retours/erreur";
        }
    }
} 