package com.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/deconnexion")
    public String deconnexion(HttpSession session) {
        // Invalider la session
        session.invalidate();
        return "redirect:/";
    }
} 