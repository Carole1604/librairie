package com.bibliotheque.controller;

import com.bibliotheque.model.Administrateur;
import com.bibliotheque.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portail_admin")
public class PortailAdminController {
    
    @Autowired
    private AuthentificationService authentificationService;
    
    @GetMapping("/connexion")
    public String connexion() {
        return "portail_admin/connexion";
    }
    
    @PostMapping("/connexion")
    public String traiterConnexion(@RequestParam String login, 
                                   @RequestParam String motDePasse, 
                                   Model model,
                                   jakarta.servlet.http.HttpSession session) {
        try {
            var admin = authentificationService.authentifierAdmin(login, motDePasse);
            if (admin.isPresent()) {
                // Stocker l'admin en session
                session.setAttribute("adminConnecte", admin.get());
                // Connexion réussie - rediriger vers le tableau de bord
                return "redirect:/portail_admin/tableau_de_bord";
            } else {
                // Connexion échouée
                model.addAttribute("erreur", "Login ou mot de passe incorrect");
                return "portail_admin/connexion";
            }
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la connexion: " + e.getMessage());
            return "portail_admin/connexion";
        }
    }

    @GetMapping("/tableau_de_bord")
    public String tableauDeBord() {
        return "portail_admin/tableau_de_bord";
    }
} 