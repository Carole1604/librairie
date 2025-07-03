package com.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portail_admin")
public class PortailAdminController {
    @GetMapping("/connexion")
    public String connexion() {
        return "portail_admin/connexion";
    }

    @GetMapping("/tableau_de_bord")
    public String tableauDeBord() {
        return "portail_admin/tableau_de_bord";
    }
} 