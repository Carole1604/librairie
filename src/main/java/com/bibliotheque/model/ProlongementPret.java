package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prolongement_pret")
public class ProlongementPret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prolongement")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pret", nullable = false)
    private Pret pret;

    @Column(name = "date_demande", nullable = false)
    private LocalDate dateDemande = LocalDate.now();

    @Column(name = "nouvelle_date_rendu", nullable = false)
    private LocalDate nouvelleDateRendu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_statut")
    private Statut statut;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @Column(name = "motif_refus")
    private String motifRefus;

    @Column(name = "nombre_jours_ajoutes")
    private Integer nombreJoursAjoutes;

    public ProlongementPret() {}
    // Getters et setters omis pour la concision
} 