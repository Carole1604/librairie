package com.bibliotheque.controller;

import com.bibliotheque.model.Penalite;
import com.bibliotheque.service.PenaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/penalites")
public class PenaliteController {
    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("")
    public String index() {
        return "redirect:/penalites/liste";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/penalites/liste";
    }

    @GetMapping("/liste")
    public String listePenalites(Model model) {
        model.addAttribute("penalites", penaliteService.findAll());
        return "penalites/liste";
    }

    @GetMapping("/details/{id}")
    public String detailsPenalite(@PathVariable Integer id, Model model) {
        Penalite penalite = penaliteService.findById(id).orElse(null);
        model.addAttribute("penalite", penalite);
        return "penalites/details";
    }
    
    /**
     * Désactive les pénalités expirées
     */
    @PostMapping("/nettoyer")
    public String nettoyerPenalitesExpirees(RedirectAttributes redirectAttributes) {
        try {
            penaliteService.desactiverPenalitesExpirees();
            redirectAttributes.addFlashAttribute("success", "Les pénalités expirées ont été désactivées avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors du nettoyage des pénalités: " + e.getMessage());
        }
        return "redirect:/penalites/liste";
    }
    
    /**
     * Désactive manuellement une pénalité spécifique
     */
    @PostMapping("/desactiver/{id}")
    public String desactiverPenalite(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Penalite penalite = penaliteService.findById(id).orElse(null);
            if (penalite == null) {
                redirectAttributes.addFlashAttribute("error", "Pénalité non trouvée.");
                return "redirect:/penalites/liste";
            }
            
            penalite.setEstActive(false);
            penaliteService.save(penalite);
            redirectAttributes.addFlashAttribute("success", "Pénalité #" + id + " désactivée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la désactivation: " + e.getMessage());
        }
        return "redirect:/penalites/liste";
    }
} 