package com.bibliotheque.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parametrage_general")
public class ParametrageGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "duree_pret", nullable = false)
    private Integer dureePret;

    @Column(name = "limite_emprunt", nullable = false)
    private Integer limiteEmprunt;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getDureePret() { return dureePret; }
    public void setDureePret(Integer dureePret) { this.dureePret = dureePret; }

    public Integer getLimiteEmprunt() { return limiteEmprunt; }
    public void setLimiteEmprunt(Integer limiteEmprunt) { this.limiteEmprunt = limiteEmprunt; }
} 