package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "livre")
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livre")
    private Integer id;

    @Column(name = "titre", nullable = false, length = 255)
    private String titre;

    @Column(name = "isbn", unique = true)
    private Integer isbn;

    @Column(name = "edition", length = 100)
    private String edition;

    @Column(name = "auteur", nullable = false, length = 255)
    private String auteur;

    @Column(name = "annee_publication")
    private Integer anneePublication;

    @Column(name = "nombre_exemplaires")
    private Integer nombreExemplaires = 0;

    @Column(name = "age_minimum")
    private Integer ageMinimum = 0;

    @Column(name = "date_ajout")
    private LocalDateTime dateAjout = LocalDateTime.now();

    public Livre() {}

    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public String getEdition() {
        return edition;
    }

    public Integer getAnneePublication() {
        return anneePublication;
    }

    public Integer getNombreExemplaires() {
        return nombreExemplaires;
    }

    public Integer getAgeMinimum() {
        return ageMinimum;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public String getAuteur() {
        return auteur;
    }

    // Getters et setters omis pour la concision
} 