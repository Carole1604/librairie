package com.bibliotheque.controller;

import com.bibliotheque.model.Adherent;
import com.bibliotheque.model.Pret;
import com.bibliotheque.service.AdherentService;
import com.bibliotheque.service.PretService;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.service.PenaliteService;
import com.bibliotheque.model.Penalite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/adherents")
public class AdherentController {
    @Autowired
    private AdherentService adherentService;
    @Autowired
    private PretService pretService;
    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("")
    public String index() {
        return "redirect:/adherents/liste";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/adherents/liste";
    }

    @GetMapping("/liste")
    public String listeAdherents(Model model) {
        model.addAttribute("adherents", adherentService.findAll());
        return "adherents/liste";
    }

    @GetMapping("/inscription")
    public String inscriptionForm(Model model) {
        model.addAttribute("adherent", new Adherent());
        return "adherents/inscription";
    }

    @PostMapping("/inscription")
    public String inscrireAdherent(@ModelAttribute Adherent adherent) {
        adherentService.save(adherent);
        return "redirect:/adherents/liste";
    }

    @GetMapping("/modifier/{id}")
    public String modifierForm(@PathVariable Integer id, Model model) {
        model.addAttribute("adherent", adherentService.findById(id).orElse(null));
        return "adherents/modifier";
    }

    @PostMapping("/modifier")
    public String modifierAdherent(@ModelAttribute Adherent adherent) {
        adherentService.save(adherent);
        return "redirect:/adherents/liste";
    }

    @PostMapping("/desactiver")
    public String desactiverAdherent(@RequestParam Integer id) {
        Adherent adherent = adherentService.findById(id).orElse(null);
        if (adherent != null) {
            adherent.setEstActif(false);
            adherentService.save(adherent);
        }
        return "redirect:/adherents/liste";
    }

    @GetMapping("/parametrage")
    public String parametrageForm() {
        return "adherents/parametrage";
    }

    @GetMapping("/{id}/prets/creation")
    public String creationPretPourAdherent(@PathVariable Integer id, Model model) {
        Adherent adherent = adherentService.findById(id).orElse(null);
        Pret pret = new Pret();
        pret.setAdherent(adherent);
        model.addAttribute("pret", pret);
        model.addAttribute("adherents", List.of(adherent));
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "prets/creation";
    }
    
    @PostMapping("/{id}/prets/creation")
    public String creerPretPourAdherent(@PathVariable Integer id, @ModelAttribute Pret pret, Model model) {
        Adherent adherent = adherentService.findById(id).orElse(null);
        pret.setAdherent(adherent);
        
        try {
            pretService.creerPret(pret);
            return "redirect:/prets/liste";
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la création du prêt: " + e.getMessage());
            model.addAttribute("pret", pret);
            model.addAttribute("adherents", List.of(adherent));
            model.addAttribute("exemplaires", exemplaireService.findAll());
            return "prets/erreur";
        }
    }

    @GetMapping("/details-json/{id}")
    @ResponseBody
    public java.util.Map<String, Object> detailsAdherentJson(@PathVariable Integer id) {
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        Adherent adherent = adherentService.findById(id).orElse(null);
        result.put("adherent", adherent);
        if (adherent != null) {
            Integer quotaMax = adherent.getQuotaMax();
            Integer quotaActuel = adherent.getQuotaActuel();
            Integer quotaRestant = (quotaMax != null && quotaActuel != null) ? quotaMax - quotaActuel : null;
            result.put("quotaRestant", quotaRestant);
            java.util.List<Penalite> penalites = penaliteService.findByAdherentAndEstActive(adherent, true);
            result.put("penalite", penalites.isEmpty() ? null : penalites.get(0));
        } else {
            result.put("quotaRestant", null);
            result.put("penalite", null);
        }
        return result;
    }
} 

//http://localhost:8080/adherents/details-json/ID