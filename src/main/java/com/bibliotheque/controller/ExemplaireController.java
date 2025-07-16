package com.bibliotheque.controller;

import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exemplaires")
public class ExemplaireController {
    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private LivreService livreService;

    @GetMapping("")
    public String index() {
        return "redirect:/livres/liste";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/livres/liste";
    }

    @GetMapping("/liste/{livreId}")
    public String listeExemplaires(@PathVariable Integer livreId, Model model) {
        Livre livre = livreService.findById(livreId).orElse(null);
        List<Exemplaire> exemplaires = exemplaireService.findByLivre(livre);
        model.addAttribute("livre", livre);
        model.addAttribute("exemplaires", exemplaires);
        return "exemplaires/liste";
    }

    @GetMapping("/ajout/{livreId}")
    public String ajoutForm(@PathVariable Integer livreId, Model model) {
        Livre livre = livreService.findById(livreId).orElse(null);
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setLivre(livre);
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("livre", livre);
        return "exemplaires/ajout";
    }

    @PostMapping("/ajout")
    public String ajouterExemplaire(@ModelAttribute Exemplaire exemplaire) {
        exemplaireService.save(exemplaire);
        return "redirect:/exemplaires/liste/" + exemplaire.getLivre().getId();
    }

    @GetMapping("/modifier/{id}")
    public String modifierForm(@PathVariable Integer id, Model model) {
        Exemplaire exemplaire = exemplaireService.findById(id).orElse(null);
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("livre", exemplaire.getLivre());
        return "exemplaires/modifier";
    }

    @PostMapping("/modifier")
    public String modifierExemplaire(@ModelAttribute Exemplaire exemplaire) {
        exemplaireService.save(exemplaire);
        return "redirect:/exemplaires/liste/" + exemplaire.getLivre().getId();
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerForm(@PathVariable Integer id, Model model) {
        Exemplaire exemplaire = exemplaireService.findById(id).orElse(null);
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("livre", exemplaire.getLivre());
        return "exemplaires/supprimer";
    }

    @PostMapping("/supprimer")
    public String supprimerExemplaire(@RequestParam Integer id, @RequestParam Integer livreId) {
        exemplaireService.deleteById(id);
        return "redirect:/exemplaires/liste/" + livreId;
    }
} 