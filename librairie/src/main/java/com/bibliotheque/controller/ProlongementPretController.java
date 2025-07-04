package com.bibliotheque.controller;

import com.bibliotheque.model.ProlongementPret;
import com.bibliotheque.service.ProlongementPretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prolongements")
public class ProlongementPretController {
    @Autowired
    private ProlongementPretService prolongementPretService;

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
    public String demandeForm(Model model) {
        model.addAttribute("prolongement", new ProlongementPret());
        return "prolongements/demande";
    }

    @PostMapping("/demande")
    public String demanderProlongement(@ModelAttribute ProlongementPret prolongementPret) {
        prolongementPretService.save(prolongementPret);
        return "redirect:/prolongements/liste";
    }

    @GetMapping("/validation/{id}")
    public String validationForm(@PathVariable Integer id, Model model) {
        ProlongementPret prolongement = prolongementPretService.findById(id).orElse(null);
        model.addAttribute("prolongement", prolongement);
        return "prolongements/validation";
    }

    @PostMapping("/validation")
    public String validerProlongement(@ModelAttribute ProlongementPret prolongementPret) {
        prolongementPretService.save(prolongementPret);
        return "redirect:/prolongements/liste";
    }
} 