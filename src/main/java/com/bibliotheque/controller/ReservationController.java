package com.bibliotheque.controller;

import com.bibliotheque.model.Reservation;
import com.bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/liste")
    public String listeReservations(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations/liste";
    }

    @GetMapping("/creation")
    public String creationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservations/creation";
    }

    @PostMapping("/creation")
    public String creerReservation(@ModelAttribute Reservation reservation) {
        reservationService.save(reservation);
        return "redirect:/reservations/liste";
    }

    @GetMapping("/validation/{id}")
    public String validationForm(@PathVariable Integer id, Model model) {
        Reservation reservation = reservationService.findById(id).orElse(null);
        model.addAttribute("reservation", reservation);
        return "reservations/validation";
    }

    @PostMapping("/validation")
    public String validerReservation(@ModelAttribute Reservation reservation) {
        reservationService.save(reservation);
        return "redirect:/reservations/liste";
    }

    @GetMapping("/annulation/{id}")
    public String annulationForm(@PathVariable Integer id, Model model) {
        Reservation reservation = reservationService.findById(id).orElse(null);
        model.addAttribute("reservation", reservation);
        return "reservations/annulation";
    }

    @PostMapping("/annulation")
    public String annulerReservation(@ModelAttribute Reservation reservation) {
        reservationService.save(reservation);
        return "redirect:/reservations/liste";
    }

    @GetMapping("/rappels")
    public String rappels(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations/rappels";
    }
} 