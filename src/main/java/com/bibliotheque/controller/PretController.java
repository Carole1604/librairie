package com.bibliotheque.controller;

import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Adherent;
import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.service.PretService;
import com.bibliotheque.service.AdherentService;
import com.bibliotheque.service.ExemplaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prets")
public class PretController {
    @Autowired
    private PretService pretService;
    @Autowired
    private AdherentService adherentService;
    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping("/liste")
    public String listePrets(Model model) {
        model.addAttribute("prets", pretService.findAll());
        return "prets/liste";
    }

    @GetMapping("/creation")
    public String creationForm(Model model) {
        model.addAttribute("pret", new Pret());
        model.addAttribute("adherents", adherentService.findAll());
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "prets/creation";
    }

    @PostMapping("/creation")
    public String creerPret(@ModelAttribute Pret pret, Model model) {
        // Ici, il faudrait ajouter la logique métier pour vérifier les règles (quota, pénalité, etc.)
        pretService.save(pret);
        return "redirect:/prets/liste";
    }

    @GetMapping("/retour/{id}")
    public String retourForm(@PathVariable Integer id, Model model) {
        Pret pret = pretService.findById(id).orElse(null);
        model.addAttribute("pret", pret);
        return "prets/retour";
    }

    @PostMapping("/retour")
    public String enregistrerRetour(@RequestParam Integer id) {
        Pret pret = pretService.findById(id).orElse(null);
        if (pret != null) {
            // Ici, il faudrait mettre à jour l'état de l'exemplaire, la date de retour, etc.
            pretService.save(pret);
        }
        return "redirect:/prets/liste";
    }
} 