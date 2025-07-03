package com.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portail_adherent")
public class PortailAdherentController {
    @GetMapping("/connexion")
    public String connexion() {
        return "portail_adherent/connexion";
    }

    @GetMapping("/profil")
    public String profil(Model model) {
        // Charger les infos du profil adhérent connecté
        return "portail_adherent/profil";
    }

    @GetMapping("/prets")
    public String prets(Model model) {
        // Charger les prêts de l'adhérent connecté
        return "portail_adherent/prets";
    }

    @GetMapping("/reservations")
    public String reservations(Model model) {
        // Charger les réservations de l'adhérent connecté
        return "portail_adherent/reservations";
    }

    @GetMapping("/penalites")
    public String penalites(Model model) {
        // Charger les pénalités de l'adhérent connecté
        return "portail_adherent/penalites";
    }

    @GetMapping("/prolongement")
    public String prolongement(Model model) {
        // Formulaire de demande de prolongement
        return "portail_adherent/prolongement";
    }
} 