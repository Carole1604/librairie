package com.bibliotheque.controller;

import com.bibliotheque.model.ParametrageGeneral;
import com.bibliotheque.service.ParametrageGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parametrage-general")
public class ParametrageGeneralController {
    @Autowired
    private ParametrageGeneralService service;

    @GetMapping
    public String showParametrage(Model model) {
        ParametrageGeneral param = service.getParametrage();
        if (param == null) param = new ParametrageGeneral();
        model.addAttribute("parametrage", param);
        return "parametrage/index";
    }

    @PostMapping
    public String updateParametrage(@ModelAttribute ParametrageGeneral parametrage, Model model) {
        service.save(parametrage);
        model.addAttribute("parametrage", parametrage);
        model.addAttribute("success", "Paramétrage mis à jour !");
        return "parametrage/index";
    }

    @GetMapping("/parametrage")
    public String redirectToGeneral() {
        return "redirect:/parametrage-general";
    }
} 