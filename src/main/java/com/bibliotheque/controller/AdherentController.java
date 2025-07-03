package com.bibliotheque.controller;

import com.bibliotheque.model.Adherent;
import com.bibliotheque.service.AdherentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adherents")
public class AdherentController {
    @Autowired
    private AdherentService adherentService;

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
} 