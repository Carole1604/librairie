package com.bibliotheque.controller;

import com.bibliotheque.model.Livre;
import com.bibliotheque.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livres")
public class LivreController {
    @Autowired
    private LivreService livreService;

    @GetMapping("/liste")
    public String listeLivres(Model model) {
        model.addAttribute("livres", livreService.findAll());
        return "livres/liste";
    }

    @GetMapping("/ajout")
    public String ajoutForm(Model model) {
        model.addAttribute("livre", new Livre());
        return "livres/ajout";
    }

    @PostMapping("/ajout")
    public String ajouterLivre(@ModelAttribute Livre livre) {
        livreService.save(livre);
        return "redirect:/livres/liste";
    }

    @GetMapping("/modifier/{id}")
    public String modifierForm(@PathVariable Integer id, Model model) {
        model.addAttribute("livre", livreService.findById(id).orElse(null));
        return "livres/modifier";
    }

    @PostMapping("/modifier")
    public String modifierLivre(@ModelAttribute Livre livre) {
        livreService.save(livre);
        return "redirect:/livres/liste";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerForm(@PathVariable Integer id, Model model) {
        model.addAttribute("livre", livreService.findById(id).orElse(null));
        return "livres/supprimer";
    }

    @PostMapping("/supprimer")
    public String supprimerLivre(@RequestParam Integer id) {
        livreService.deleteById(id);
        return "redirect:/livres/liste";
    }
} 