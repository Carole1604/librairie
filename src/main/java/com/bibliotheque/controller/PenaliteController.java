package com.bibliotheque.controller;

import com.bibliotheque.model.Penalite;
import com.bibliotheque.service.PenaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/penalites")
public class PenaliteController {
    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("/liste")
    public String listePenalites(Model model) {
        model.addAttribute("penalites", penaliteService.findAll());
        return "penalites/liste";
    }

    @GetMapping("/details/{id}")
    public String detailsPenalite(@PathVariable Integer id, Model model) {
        Penalite penalite = penaliteService.findById(id).orElse(null);
        model.addAttribute("penalite", penalite);
        return "penalites/details";
    }
} 