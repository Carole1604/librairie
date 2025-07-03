package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "adherent")
public class Adherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adherent")
    private Integer id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "est_etudiant")
    private Boolean estEtudiant = false;

    @Column(name = "est_professionnel")
    private Boolean estProfessionnel = false;

    @Column(name = "est_anonyme")
    private Boolean estAnonyme = true;

    @Column(name = "quota_max")
    private Integer quotaMax = 3;

    @Column(name = "quota_actuel")
    private Integer quotaActuel = 0;

    @Column(name = "est_actif")
    private Boolean estActif = true;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    public Adherent() {}

    public void setEstActif(boolean estActif) { this.estActif = estActif; }

    // Getters et setters omis pour la concision
} 