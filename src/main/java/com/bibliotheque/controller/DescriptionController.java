package com.bibliotheque.controller;

import com.bibliotheque.model.Description;
import com.bibliotheque.model.Livre;
import com.bibliotheque.service.DescriptionService;
import com.bibliotheque.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/descriptions")
public class DescriptionController {
    @Autowired
    private DescriptionService descriptionService;
    @Autowired
    private LivreService livreService;

    @GetMapping("/ajout/{livreId}")
    public String ajoutForm(@PathVariable Integer livreId, Model model) {
        Livre livre = livreService.findById(livreId).orElse(null);
        Description description = new Description();
        description.setLivre(livre);
        model.addAttribute("description", description);
        model.addAttribute("livre", livre);
        return "descriptions/ajout";
    }

    @PostMapping("/ajout")
    public String ajouterDescription(@ModelAttribute Description description) {
        descriptionService.save(description);
        return "redirect:/livres/liste";
    }

    @GetMapping("/modifier/{id}")
    public String modifierForm(@PathVariable Integer id, Model model) {
        Description description = descriptionService.findById(id).orElse(null);
        model.addAttribute("description", description);
        model.addAttribute("livre", description.getLivre());
        return "descriptions/modifier";
    }

    @PostMapping("/modifier")
    public String modifierDescription(@ModelAttribute Description description) {
        descriptionService.save(description);
        return "redirect:/livres/liste";
    }
} 