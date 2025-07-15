package com.bibliotheque.controller;

import com.bibliotheque.model.Livre;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.model.Exemplaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/livres")
public class LivreController {
    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping("")
    public String index() {
        return "redirect:/livres/liste";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/livres/liste";
    }

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

    @GetMapping("/details/{id}")
    public String detailsLivre(@PathVariable Integer id, Model model) {
        model.addAttribute("livre", livreService.findById(id).orElse(null));
        return "livres/details";
    }

    @GetMapping("/details-json/{id}")
    @ResponseBody
    public Map<String, Object> detailsLivreJson(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        Livre livre = livreService.findById(id).orElse(null);
        result.put("livre", livre);
        List<Exemplaire> exemplairesDisponibles = livre != null ? exemplaireService.findDisponiblesByLivre(livre) : Collections.emptyList();
        result.put("exemplairesDisponibles", exemplairesDisponibles);
        return result;
    }

    @PostMapping("/supprimer")
    public String supprimerLivre(@RequestParam Integer id) {
        livreService.deleteById(id);
        return "redirect:/livres/liste";
    }
} 

//http://localhost:8080/livres/details-json/ID