package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherent adherent;

    @ManyToOne
    @JoinColumn(name = "id_livre")
    private Livre livre;

    @Column(name = "date_reservation")
    private LocalDate dateReservation;

    @ManyToOne
    @JoinColumn(name = "id_statut")
    private Statut statut;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @Column(name = "date_annulation")
    private LocalDate dateAnnulation;

    @Column(name = "motif_annulation")
    private String motifAnnulation;

    public Reservation() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }

    public LocalDate getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDate dateReservation) { this.dateReservation = dateReservation; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

    public LocalDate getDateValidation() { return dateValidation; }
    public void setDateValidation(LocalDate dateValidation) { this.dateValidation = dateValidation; }

    public LocalDate getDateAnnulation() { return dateAnnulation; }
    public void setDateAnnulation(LocalDate dateAnnulation) { this.dateAnnulation = dateAnnulation; }

    public String getMotifAnnulation() { return motifAnnulation; }
    public void setMotifAnnulation(String motifAnnulation) { this.motifAnnulation = motifAnnulation; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                '}';
    }
} 