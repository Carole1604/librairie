package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tableau_de_bord")
public class TableauDeBord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statistique")
    private Integer id;

    @Column(name = "date_stat")
    private LocalDate dateStat;

    @Column(name = "nombre_prets")
    private Integer nombrePrets;

    @Column(name = "nombre_retours")
    private Integer nombreRetours;

    @Column(name = "nombre_reservations")
    private Integer nombreReservations;

    @Column(name = "nombre_penalites")
    private Integer nombrePenalites;

    @Column(name = "nombre_adherents_actifs")
    private Integer nombreAdherentsActifs;

    @Column(name = "nombre_livres_disponibles")
    private Integer nombreLivresDisponibles;

    public TableauDeBord() {}

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDateStat() { return dateStat; }
    public void setDateStat(LocalDate dateStat) { this.dateStat = dateStat; }

    public Integer getNombrePrets() { return nombrePrets; }
    public void setNombrePrets(Integer nombrePrets) { this.nombrePrets = nombrePrets; }

    public Integer getNombreRetours() { return nombreRetours; }
    public void setNombreRetours(Integer nombreRetours) { this.nombreRetours = nombreRetours; }

    public Integer getNombreReservations() { return nombreReservations; }
    public void setNombreReservations(Integer nombreReservations) { this.nombreReservations = nombreReservations; }

    public Integer getNombrePenalites() { return nombrePenalites; }
    public void setNombrePenalites(Integer nombrePenalites) { this.nombrePenalites = nombrePenalites; }

    public Integer getNombreAdherentsActifs() { return nombreAdherentsActifs; }
    public void setNombreAdherentsActifs(Integer nombreAdherentsActifs) { this.nombreAdherentsActifs = nombreAdherentsActifs; }

    public Integer getNombreLivresDisponibles() { return nombreLivresDisponibles; }
    public void setNombreLivresDisponibles(Integer nombreLivresDisponibles) { this.nombreLivresDisponibles = nombreLivresDisponibles; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableauDeBord that = (TableauDeBord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "TableauDeBord{" +
                "id=" + id +
                ", dateStat=" + dateStat +
                '}';
    }
} 