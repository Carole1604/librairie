package com.bibliotheque.controller;

import com.bibliotheque.model.JourFerie;
import com.bibliotheque.service.JourFerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/joursferies")
public class JourFerieController {
    @Autowired
    private JourFerieService jourFerieService;

    @GetMapping("/liste")
    public String listeJoursFeries(Model model) {
        model.addAttribute("joursFeries", jourFerieService.findAll());
        return "jour_ferie/liste";
    }

    @GetMapping("/ajout")
    public String ajoutForm(Model model) {
        model.addAttribute("jourFerie", new JourFerie());
        return "jour_ferie/ajout";
    }

    @PostMapping("/ajout")
    public String ajouterJourFerie(@ModelAttribute JourFerie jourFerie) {
        jourFerieService.save(jourFerie);
        return "redirect:/joursferies/liste";
    }

    @GetMapping("/modifier/{id}")
    public String modifierForm(@PathVariable Integer id, Model model) {
        model.addAttribute("jourFerie", jourFerieService.findById(id).orElse(null));
        return "jour_ferie/modifier";
    }

    @PostMapping("/modifier")
    public String modifierJourFerie(@ModelAttribute JourFerie jourFerie) {
        jourFerieService.save(jourFerie);
        return "redirect:/joursferies/liste";
    }

    @PostMapping("/supprimer")
    public String supprimerJourFerie(@RequestParam Integer id) {
        jourFerieService.deleteById(id);
        return "redirect:/joursferies/liste";
    }

    @GetMapping("")
    public String index() {
        return "jour_ferie/index";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "jour_ferie/index";
    }
} 