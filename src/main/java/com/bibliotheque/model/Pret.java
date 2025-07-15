package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pret")
public class Pret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pret")
    private Integer id;

    @Column(name = "type_pret")
    private String typePret;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin_prevue")
    private LocalDate dateFinPrevue;

    @Column(name = "date_rendu_reelle")
    private LocalDate dateRenduReelle;

    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherent adherent;

    @ManyToOne
    @JoinColumn(name = "id_exemplaire")
    private Exemplaire exemplaire;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "id_statut")
    private Statut statut;

    @Column(name = "duree_pret_jours")
    private Integer dureePretJours;

    @Column(name = "est_prolonge")
    private Boolean estProlonge = false;

    public Pret() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTypePret() { return typePret; }
    public void setTypePret(String typePret) { this.typePret = typePret; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFinPrevue() { return dateFinPrevue; }
    public void setDateFinPrevue(LocalDate dateFinPrevue) { this.dateFinPrevue = dateFinPrevue; }

    public LocalDate getDateRenduReelle() { return dateRenduReelle; }
    public void setDateRenduReelle(LocalDate dateRenduReelle) { this.dateRenduReelle = dateRenduReelle; }

    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }

    public Exemplaire getExemplaire() { return exemplaire; }
    public void setExemplaire(Exemplaire exemplaire) { this.exemplaire = exemplaire; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

    public Integer getDureePretJours() { return dureePretJours; }
    public void setDureePretJours(Integer dureePretJours) { this.dureePretJours = dureePretJours; }

    public Boolean getEstProlonge() { return estProlonge; }
    public void setEstProlonge(Boolean estProlonge) { this.estProlonge = estProlonge; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pret pret = (Pret) o;
        return Objects.equals(id, pret.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pret{" +
                "id=" + id +
                ", typePret='" + typePret + '\'' +
                '}';
    }
} 