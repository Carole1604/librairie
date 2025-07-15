package com.bibliotheque.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parametrage_general")
public class ParametrageGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Durées de prêt par type d'adhérent (en jours)
    @Column(name = "duree_pret_etudiant", nullable = false)
    private Integer dureePretEtudiant = 14; // 2 semaines par défaut

    @Column(name = "duree_pret_professionnel", nullable = false)
    private Integer dureePretProfessionnel = 21; // 3 semaines par défaut

    @Column(name = "duree_pret_anonyme", nullable = false)
    private Integer dureePretAnonyme = 7; // 1 semaine par défaut

    // Limites d'emprunt par type d'adhérent
    @Column(name = "quota_max_etudiant", nullable = false)
    private Integer quotaMaxEtudiant = 5;

    @Column(name = "quota_max_professionnel", nullable = false)
    private Integer quotaMaxProfessionnel = 8;

    @Column(name = "quota_max_anonyme", nullable = false)
    private Integer quotaMaxAnonyme = 2;

    // Paramètres généraux
    @Column(name = "duree_prolongation", nullable = false)
    private Integer dureeProlongation = 7; // Durée de prolongation en jours

    @Column(name = "nombre_prolongations_max", nullable = false)
    private Integer nombreProlongationsMax = 1; // Nombre max de prolongations

    public ParametrageGeneral() {}

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getDureePretEtudiant() { return dureePretEtudiant; }
    public void setDureePretEtudiant(Integer dureePretEtudiant) { this.dureePretEtudiant = dureePretEtudiant; }

    public Integer getDureePretProfessionnel() { return dureePretProfessionnel; }
    public void setDureePretProfessionnel(Integer dureePretProfessionnel) { this.dureePretProfessionnel = dureePretProfessionnel; }

    public Integer getDureePretAnonyme() { return dureePretAnonyme; }
    public void setDureePretAnonyme(Integer dureePretAnonyme) { this.dureePretAnonyme = dureePretAnonyme; }

    public Integer getQuotaMaxEtudiant() { return quotaMaxEtudiant; }
    public void setQuotaMaxEtudiant(Integer quotaMaxEtudiant) { this.quotaMaxEtudiant = quotaMaxEtudiant; }

    public Integer getQuotaMaxProfessionnel() { return quotaMaxProfessionnel; }
    public void setQuotaMaxProfessionnel(Integer quotaMaxProfessionnel) { this.quotaMaxProfessionnel = quotaMaxProfessionnel; }

    public Integer getQuotaMaxAnonyme() { return quotaMaxAnonyme; }
    public void setQuotaMaxAnonyme(Integer quotaMaxAnonyme) { this.quotaMaxAnonyme = quotaMaxAnonyme; }

    public Integer getDureeProlongation() { return dureeProlongation; }
    public void setDureeProlongation(Integer dureeProlongation) { this.dureeProlongation = dureeProlongation; }

    public Integer getNombreProlongationsMax() { return nombreProlongationsMax; }
    public void setNombreProlongationsMax(Integer nombreProlongationsMax) { this.nombreProlongationsMax = nombreProlongationsMax; }

    /**
     * Obtient la durée de prêt selon le type d'adhérent
     */
    public Integer getDureePretParType(Boolean estEtudiant, Boolean estProfessionnel, Boolean estAnonyme) {
        if (estEtudiant != null && estEtudiant) {
            return dureePretEtudiant;
        } else if (estProfessionnel != null && estProfessionnel) {
            return dureePretProfessionnel;
        } else if (estAnonyme != null && estAnonyme) {
            return dureePretAnonyme;
        }
        // Par défaut, considérer comme anonyme
        return dureePretAnonyme;
    }

    /**
     * Obtient le quota maximum selon le type d'adhérent
     */
    public Integer getQuotaMaxParType(Boolean estEtudiant, Boolean estProfessionnel, Boolean estAnonyme) {
        if (estEtudiant != null && estEtudiant) {
            return quotaMaxEtudiant;
        } else if (estProfessionnel != null && estProfessionnel) {
            return quotaMaxProfessionnel;
        } else if (estAnonyme != null && estAnonyme) {
            return quotaMaxAnonyme;
        }
        // Par défaut, considérer comme anonyme
        return quotaMaxAnonyme;
    }
} 