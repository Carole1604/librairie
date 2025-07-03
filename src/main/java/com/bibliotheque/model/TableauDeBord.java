package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tableau_de_bord")
public class TableauDeBord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statist")
    private Integer id;

    @Column(name = "date_stat", nullable = false, unique = true)
    private LocalDate dateStat = LocalDate.now();

    @Column(name = "nombre_prets")
    private Integer nombrePrets = 0;

    @Column(name = "nombre_retours")
    private Integer nombreRetours = 0;

    @Column(name = "nombre_reservations")
    private Integer nombreReservations = 0;

    @Column(name = "nombre_penalites")
    private Integer nombrePenalites = 0;

    @Column(name = "nombre_adherents_actifs")
    private Integer nombreAdherentsActifs = 0;

    @Column(name = "nombre_livres_disponibles")
    private Integer nombreLivresDisponibles = 0;

    public TableauDeBord() {}

    public java.time.LocalDate getDateStat() { return dateStat; }

    // Getters et setters omis pour la concision
} 