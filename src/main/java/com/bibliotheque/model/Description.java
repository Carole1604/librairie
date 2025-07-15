package com.bibliotheque.model;

import jakarta.persistence.*;

@Entity
@Table(name = "description")
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_description")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @Column(name = "resume")
    private String resume;

    @Column(name = "langue", length = 50)
    private String langue = "Français";

    @Column(name = "nombre_pages")
    private Integer nombrePages;

    @Column(name = "categorie", length = 100)
    private String categorie;

    @Column(name = "editeur", length = 150)
    private String editeur;

    public Description() {}

    public Livre getLivre() { return livre; }
    public String getLangue() { return langue; }
    public String getCategorie() { return categorie; }
    public void setLivre(Livre livre) { this.livre = livre; }

    // Getters et setters omis pour la concision
} 