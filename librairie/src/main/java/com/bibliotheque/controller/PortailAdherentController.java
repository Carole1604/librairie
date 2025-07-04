package com.bibliotheque.controller;

import com.bibliotheque.model.Adherent;
import com.bibliotheque.model.Pret;
import com.bibliotheque.service.AuthentificationService;
import com.bibliotheque.service.PretService;
import com.bibliotheque.service.ExemplaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portail_adherent")
public class PortailAdherentController {
    
    @Autowired
    private AuthentificationService authentificationService;
    
    @Autowired
    private PretService pretService;
    
    @Autowired
    private ExemplaireService exemplaireService;
    
    @GetMapping("/connexion")
    public String connexion() {
        return "portail_adherent/connexion";
    }
    
    @PostMapping("/connexion")
    public String traiterConnexion(@RequestParam String login, 
                                   @RequestParam String motDePasse, 
                                   Model model,
                                   jakarta.servlet.http.HttpSession session) {
        try {
            System.out.println("Tentative de connexion pour: " + login);
            var adherent = authentificationService.authentifierAdherent(login, motDePasse);
            if (adherent.isPresent()) {
                System.out.println("Adhérent trouvé: " + adherent.get().getNom() + " " + adherent.get().getPrenom());
                // Stocker l'adhérent en session
                session.setAttribute("adherentConnecte", adherent.get());
                // Connexion réussie - rediriger vers le profil
                return "redirect:/portail_adherent/profil";
            } else {
                System.out.println("Aucun adhérent trouvé pour le login: " + login);
                // Connexion échouée
                model.addAttribute("erreur", "Login ou mot de passe incorrect");
                return "portail_adherent/connexion";
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("erreur", "Erreur lors de la connexion: " + e.getMessage());
            return "portail_adherent/connexion";
        }
    }

    @GetMapping("/profil")
    public String profil(Model model, jakarta.servlet.http.HttpSession session) {
        // Récupérer l'adhérent connecté depuis la session
        Adherent adherent = (Adherent) session.getAttribute("adherentConnecte");
        if (adherent != null) {
            model.addAttribute("adherent", adherent);
        }
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

    @GetMapping("/prets/creation")
    public String creationPretPourAdherentConnecte(Model model, jakarta.servlet.http.HttpSession session) {
        Adherent adherent = (Adherent) session.getAttribute("adherentConnecte");
        if (adherent == null) {
            return "redirect:/portail_adherent/connexion";
        }
        Pret pret = new Pret();
        pret.setAdherent(adherent);
        model.addAttribute("pret", pret);
        model.addAttribute("adherents", java.util.List.of(adherent));
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "prets/creation";
    }
} 