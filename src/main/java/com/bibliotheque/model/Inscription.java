package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscription")
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscription")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;

    @Column(name = "date_inscription", nullable = false)
    private LocalDate dateInscription = LocalDate.now();

    @Column(name = "statut_inscription", length = 20)
    private String statutInscription = "actif";

    @Column(name = "date_fin")
    private LocalDate dateFin;

    public Inscription() {}
    // Getters et setters omis pour la concision

    public LocalDate getDateInscription() {
        return dateInscription;
    }
    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}