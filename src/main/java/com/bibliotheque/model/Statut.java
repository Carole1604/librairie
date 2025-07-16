package com.bibliotheque.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "statut")
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut")
    private Integer id;

    @Column(name = "nom_statut", nullable = false, unique = true, length = 50)
    private String nomStatut;

    public Statut() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNomStatut() { return nomStatut; }
    public void setNomStatut(String nomStatut) { this.nomStatut = nomStatut; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statut statut = (Statut) o;
        return Objects.equals(id, statut.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Statut{" +
                "id=" + id +
                ", nomStatut='" + nomStatut + '\'' +
                '}';
    }
} 