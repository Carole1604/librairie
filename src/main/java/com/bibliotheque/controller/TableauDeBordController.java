package com.bibliotheque.controller;

import com.bibliotheque.model.TableauDeBord;
import com.bibliotheque.service.TableauDeBordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tableau_de_bord")
public class TableauDeBordController {
    @Autowired
    private TableauDeBordService tableauDeBordService;

    @GetMapping("/statistiques")
    public String statistiques(@RequestParam(value = "date", required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               Model model) {
        if (date == null) date = LocalDate.now();
        
        // Récupérer les statistiques pour la date demandée
        TableauDeBord stats = tableauDeBordService.getStatistiquesPourDate(date);
        
        // Récupérer les statistiques des 7 derniers jours pour les graphiques
        List<TableauDeBord> stats7Jours = tableauDeBordService.getStatistiques7DerniersJours();
        
        model.addAttribute("stats", stats);
        model.addAttribute("date", date);
        model.addAttribute("stats7Jours", stats7Jours);
        model.addAttribute("aujourdhui", LocalDate.now());
        
        return "tableau_de_bord/statistiques";
    }

    @GetMapping("")
    public String index() {
        return "redirect:/tableau_de_bord/statistiques";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/tableau_de_bord/statistiques";
    }
    
    @GetMapping("/mois")
    public String statistiquesMois(Model model) {
        List<TableauDeBord> statsMois = tableauDeBordService.getStatistiquesMoisEnCours();
        model.addAttribute("statsMois", statsMois);
        model.addAttribute("moisEnCours", LocalDate.now().getMonth().toString());
        return "tableau_de_bord/mois";
    }
    
    @GetMapping("/historique")
    public String historique(@RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "20") int size,
                           Model model) {
        List<TableauDeBord> allStats = tableauDeBordService.getAllStatistiques();
        
        // Pagination simple
        int totalStats = allStats.size();
        int totalPages = (int) Math.ceil((double) totalStats / size);
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalStats);
        
        List<TableauDeBord> statsPage = allStats.subList(startIndex, endIndex);
        
        model.addAttribute("stats", statsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalStats", totalStats);
        
        return "tableau_de_bord/historique";
    }
    
    @PostMapping("/mettre-a-jour")
    public String mettreAJourStatistiques(@RequestParam(value = "date", required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                         RedirectAttributes redirectAttributes) {
        try {
            if (date == null) {
                tableauDeBordService.mettreAJourStatistiquesAujourdhui();
                redirectAttributes.addFlashAttribute("success", "Statistiques d'aujourd'hui mises à jour avec succès !");
            } else {
                tableauDeBordService.mettreAJourStatistiques(date);
                redirectAttributes.addFlashAttribute("success", "Statistiques du " + date + " mises à jour avec succès !");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour : " + e.getMessage());
        }
        
        return "redirect:/tableau_de_bord/statistiques?date=" + (date != null ? date : LocalDate.now());
    }
    
    @PostMapping("/nettoyer")
    public String nettoyerAnciennesStatistiques(RedirectAttributes redirectAttributes) {
        try {
            tableauDeBordService.nettoyerAnciennesStatistiques();
            redirectAttributes.addFlashAttribute("success", "Anciennes statistiques nettoyées avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors du nettoyage : " + e.getMessage());
        }
        
        return "redirect:/tableau_de_bord/historique";
    }
} 