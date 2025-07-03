package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @Column(name = "date_reservation", nullable = false)
    private LocalDate dateReservation = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_statut")
    private Statut statut;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @Column(name = "date_annulation")
    private LocalDate dateAnnulation;

    @Column(name = "motif_annulation")
    private String motifAnnulation;

    public Reservation() {}
    // Getters et setters omis pour la concision
} 