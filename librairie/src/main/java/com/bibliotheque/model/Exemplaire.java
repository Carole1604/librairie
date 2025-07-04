package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exemplaire")
public class Exemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exemplaire")
    private Integer id;

    @Column(name = "nom_exemplaire", nullable = false, length = 100)
    private String nomExemplaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @Column(name = "etat", length = 20)
    private String etat = "disponible";

    @Column(name = "date_acquisition")
    private LocalDate dateAcquisition = LocalDate.now();

    @Column(name = "localisation", length = 100)
    private String localisation;

    @Column(name = "code_barre", unique = true, length = 50)
    private String codeBarre;

    public Exemplaire() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomExemplaire() {
        return nomExemplaire;
    }

    public void setNomExemplaire(String nomExemplaire) {
        this.nomExemplaire = nomExemplaire;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDate getDateAcquisition() {
        return dateAcquisition;
    }

    public void setDateAcquisition(LocalDate dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }
} 