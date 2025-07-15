package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "jour_ferie")
public class JourFerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jour_ferie")
    private Integer id;

    @Column(name = "nom_jour_ferie", nullable = false, length = 100)
    private String nomJourFerie;

    @Column(name = "date_ferie", nullable = false, unique = true)
    private LocalDate dateFerie;

    public JourFerie() {}
    public JourFerie(String nomJourFerie, LocalDate dateFerie) {
        this.nomJourFerie = nomJourFerie;
        this.dateFerie = dateFerie;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNomJourFerie() { return nomJourFerie; }
    public void setNomJourFerie(String nomJourFerie) { this.nomJourFerie = nomJourFerie; }
    public LocalDate getDateFerie() { return dateFerie; }
    public void setDateFerie(LocalDate dateFerie) { this.dateFerie = dateFerie; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JourFerie that = (JourFerie) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
} 