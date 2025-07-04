package com.bibliotheque.controller;

import com.bibliotheque.model.TableauDeBord;
import com.bibliotheque.service.TableauDeBordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

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
        TableauDeBord stats = tableauDeBordService.getStatistiquesPourDate(date);
        model.addAttribute("stats", stats);
        model.addAttribute("date", date);
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
} 