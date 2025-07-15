package com.bibliotheque.controller;

import com.bibliotheque.model.ParametrageGeneral;
import com.bibliotheque.service.ParametrageGeneralService;
import com.bibliotheque.service.AdherentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/parametrage")
public class ParametrageController {
    
    @Autowired
    private ParametrageGeneralService parametrageService;
    
    @Autowired
    private AdherentService adherentService;

    @GetMapping("")
    public String index() {
        return "redirect:/parametrage/durees_pret";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/parametrage/durees_pret";
    }

    @GetMapping("/durees_pret")
    public String dureesPret(Model model) {
        ParametrageGeneral parametrage = parametrageService.getParametrage();
        model.addAttribute("parametrage", parametrage);
        return "parametrage/durees_pret";
    }

    @PostMapping("/durees_pret")
    public String sauvegarderDureesPret(@ModelAttribute ParametrageGeneral parametrage, 
                                       RedirectAttributes redirectAttributes) {
        try {
            // Récupérer le paramétrage existant et mettre à jour seulement les durées
            ParametrageGeneral existant = parametrageService.getParametrage();
            if (existant != null) {
                existant.setDureePretEtudiant(parametrage.getDureePretEtudiant());
                existant.setDureePretProfessionnel(parametrage.getDureePretProfessionnel());
                existant.setDureePretAnonyme(parametrage.getDureePretAnonyme());
                existant.setDureeProlongation(parametrage.getDureeProlongation());
                existant.setNombreProlongationsMax(parametrage.getNombreProlongationsMax());
                parametrageService.save(existant);
            } else {
                parametrageService.save(parametrage);
            }
            redirectAttributes.addFlashAttribute("success", "Durées de prêt mises à jour avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la sauvegarde : " + e.getMessage());
        }
        return "redirect:/parametrage/durees_pret";
    }

    @GetMapping("/limites_emprunt")
    public String limitesEmprunt(Model model) {
        ParametrageGeneral parametrage = parametrageService.getParametrage();
        model.addAttribute("parametrage", parametrage);
        return "parametrage/limites_emprunt";
    }

    @PostMapping("/limites_emprunt")
    public String sauvegarderLimitesEmprunt(@ModelAttribute ParametrageGeneral parametrage, 
                                           RedirectAttributes redirectAttributes) {
        try {
            // Récupérer le paramétrage existant et mettre à jour seulement les quotas
            ParametrageGeneral existant = parametrageService.getParametrage();
            if (existant != null) {
                existant.setQuotaMaxEtudiant(parametrage.getQuotaMaxEtudiant());
                existant.setQuotaMaxProfessionnel(parametrage.getQuotaMaxProfessionnel());
                existant.setQuotaMaxAnonyme(parametrage.getQuotaMaxAnonyme());
                parametrageService.save(existant);
            } else {
                parametrageService.save(parametrage);
            }
            redirectAttributes.addFlashAttribute("success", "Limites d'emprunt mises à jour avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la sauvegarde : " + e.getMessage());
        }
        return "redirect:/parametrage/limites_emprunt";
    }
    
    @PostMapping("/mettre-a-jour-quotas")
    public String mettreAJourQuotasAdherents(RedirectAttributes redirectAttributes) {
        try {
            adherentService.mettreAJourQuotasTousAdherents();
            redirectAttributes.addFlashAttribute("success", "Quotas de tous les adhérents mis à jour avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour des quotas : " + e.getMessage());
        }
        return "redirect:/parametrage/limites_emprunt";
    }
} 