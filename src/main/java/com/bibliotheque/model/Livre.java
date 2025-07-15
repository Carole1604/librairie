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
    private String isbn;

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

    @Column(name = "image_path", length = 500)
    private String imagePath;

    @Column(name = "date_ajout")
    private LocalDateTime dateAjout = LocalDateTime.now();

    public Livre() {}

    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getIsbn() {
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

    public String getImagePath() {
        return imagePath;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }

    public void setNombreExemplaires(Integer nombreExemplaires) {
        this.nombreExemplaires = nombreExemplaires;
    }

    public void setAgeMinimum(Integer ageMinimum) {
        this.ageMinimum = ageMinimum;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livre livre = (Livre) o;
        return Objects.equals(id, livre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
} 