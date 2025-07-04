package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "recherche")
public class Recherche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recherche")
    private Integer id;

    @Column(name = "critere", length = 255)
    private String critere;

    @Column(name = "date_recherche")
    private LocalDateTime dateRecherche;

    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherent adherent;

    public Recherche() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCritere() { return critere; }
    public void setCritere(String critere) { this.critere = critere; }

    public LocalDateTime getDateRecherche() { return dateRecherche; }
    public void setDateRecherche(LocalDateTime dateRecherche) { this.dateRecherche = dateRecherche; }

    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recherche that = (Recherche) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Recherche{" +
                "id=" + id +
                ", critere='" + critere + '\'' +
                '}';
    }
} 