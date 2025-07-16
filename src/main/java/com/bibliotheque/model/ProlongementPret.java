package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "prolongement_pret")
public class ProlongementPret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prolongement")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pret")
    private Pret pret;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @Column(name = "nouvelle_date_rendu")
    private LocalDate nouvelleDateRendu;

    @ManyToOne
    @JoinColumn(name = "id_statut")
    private Statut statut;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @Column(name = "motif_refus")
    private String motifRefus;

    @Column(name = "nombre_jours_ajoutes")
    private Integer nombreJoursAjoutes;

    public ProlongementPret() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Pret getPret() { return pret; }
    public void setPret(Pret pret) { this.pret = pret; }

    public LocalDate getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDate dateDemande) { this.dateDemande = dateDemande; }

    public LocalDate getNouvelleDateRendu() { return nouvelleDateRendu; }
    public void setNouvelleDateRendu(LocalDate nouvelleDateRendu) { this.nouvelleDateRendu = nouvelleDateRendu; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

    public LocalDate getDateValidation() { return dateValidation; }
    public void setDateValidation(LocalDate dateValidation) { this.dateValidation = dateValidation; }

    public String getMotifRefus() { return motifRefus; }
    public void setMotifRefus(String motifRefus) { this.motifRefus = motifRefus; }

    public Integer getNombreJoursAjoutes() { return nombreJoursAjoutes; }
    public void setNombreJoursAjoutes(Integer nombreJoursAjoutes) { this.nombreJoursAjoutes = nombreJoursAjoutes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProlongementPret that = (ProlongementPret) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProlongementPret{" +
                "id=" + id +
                '}';
    }
} 