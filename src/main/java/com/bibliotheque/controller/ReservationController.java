package com.bibliotheque.controller;

import com.bibliotheque.model.Reservation;
import com.bibliotheque.model.Adherent;
import com.bibliotheque.model.Statut;
import com.bibliotheque.service.ReservationService;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.AdherentService;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.repository.InscriptionRepository;
import com.bibliotheque.model.Inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private LivreService livreService;
    @Autowired
    private AdherentService adherentService;
    @Autowired
    private StatutRepository statutRepository;
    @Autowired
    private InscriptionRepository inscriptionRepository;

    @GetMapping("")
    public String index() {
        return "redirect:/reservations/liste";
    }

    @GetMapping("/")
    public String indexSlash() {
        return "redirect:/reservations/liste";
    }

    @GetMapping("/liste")
    public String listeReservations(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations/liste";
    }

    @GetMapping("/creation")
    public String creationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("livres", livreService.findAll());
        return "reservations/creation";
    }

    @PostMapping("/creation")
    public String creerReservation(@ModelAttribute Reservation reservation, jakarta.servlet.http.HttpSession session, Model model) {
        // Récupérer l'adhérent connecté depuis la session
        Adherent adherent = (Adherent) session.getAttribute("adherentConnecte");
        if (adherent == null) {
            return "redirect:/portail_adherent/connexion";
        }
        // Vérification de la période d'abonnement
        Inscription inscription = inscriptionRepository.findByAdherent_Id(adherent.getId());
        java.time.LocalDate today = java.time.LocalDate.now();
        if (inscription == null || today.isBefore(inscription.getDateInscription()) || today.isAfter(inscription.getDateFin())) {
            model.addAttribute("erreur", "Vous ne pouvez réserver que pendant la période de votre abonnement (" +
                (inscription != null ? inscription.getDateInscription() : "?") + " au " + (inscription != null ? inscription.getDateFin() : "?") + ")");
            model.addAttribute("reservation", reservation);
            model.addAttribute("livres", livreService.findAll());
            return "reservations/creation";
        }
        reservation.setAdherent(adherent);
        // Initialiser la date de réservation
        reservation.setDateReservation(java.time.LocalDate.now());
        // Initialiser le statut à 'en_cours' (id = 1)
        Statut statutEnCours = statutRepository.findById(1).orElse(null);
        reservation.setStatut(statutEnCours);
        reservationService.save(reservation);
        return "redirect:/portail_adherent/reservations";
    }

    @GetMapping("/validation/{id}")
    public String validationForm(@PathVariable Integer id, Model model) {
        Reservation reservation = reservationService.findById(id).orElse(null);
        model.addAttribute("reservation", reservation);
        return "reservations/validation";
    }

    @PostMapping("/validation")
    public String validerReservation(@ModelAttribute Reservation reservation) {
        Reservation existing = reservationService.findById(reservation.getId()).orElse(null);
        if (existing != null) {
            // Met à jour seulement les champs de validation
            existing.setStatut(reservation.getStatut());
            // Ajoute ici d'autres champs modifiables si besoin
            reservationService.save(existing);
        }
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