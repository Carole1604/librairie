package com.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parametrage")
public class ParametrageController {
    @GetMapping("/durees_pret")
    public String dureesPret(Model model) {
        // Ici, on pourrait charger les valeurs actuelles depuis la base ou une config
        return "parametrage/durees_pret";
    }

    @GetMapping("/limites_emprunt")
    public String limitesEmprunt(Model model) {
        // Ici, on pourrait charger les valeurs actuelles depuis la base ou une config
        return "parametrage/limites_emprunt";
    }
} 