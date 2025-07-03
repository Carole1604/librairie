package com.bibliotheque.controller;

import com.bibliotheque.model.TableauDeBord;
import com.bibliotheque.service.TableauDeBordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/tableau_de_bord")
public class TableauDeBordController {
    @Autowired
    private TableauDeBordService tableauDeBordService;

    @GetMapping("/statistiques")
    public String statistiques(@RequestParam(required = false) String date, Model model) {
        LocalDate dateStat = (date != null) ? LocalDate.parse(date) : LocalDate.now();
        Optional<TableauDeBord> stats = tableauDeBordService.findByDate(dateStat);
        model.addAttribute("stats", stats.orElse(null));
        model.addAttribute("dateStat", dateStat);
        return "tableau_de_bord/statistiques";
    }
} 