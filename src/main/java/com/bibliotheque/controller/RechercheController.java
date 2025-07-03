package com.bibliotheque.controller;

import com.bibliotheque.model.Livre;
import com.bibliotheque.service.RechercheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/recherche")
public class RechercheController {
    @Autowired
    private RechercheService rechercheService;

    @GetMapping("")
    public String recherche(@RequestParam(required = false) String titre,
                            @RequestParam(required = false) String auteur,
                            @RequestParam(required = false) String langue,
                            @RequestParam(required = false) String categorie,
                            Model model) {
        List<Livre> resultats = rechercheService.rechercheLivres(titre, auteur, langue, categorie);
        model.addAttribute("resultats", resultats);
        model.addAttribute("titre", titre);
        model.addAttribute("auteur", auteur);
        model.addAttribute("langue", langue);
        model.addAttribute("categorie", categorie);
        return "recherche/recherche";
    }
} 