package com.bibliotheque.service;

import com.bibliotheque.model.ParametrageGeneral;
import com.bibliotheque.model.Adherent;
import com.bibliotheque.repository.ParametrageGeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ParametrageGeneralService {
    @Autowired
    private ParametrageGeneralRepository repository;

    public ParametrageGeneral getParametrage() {
        return repository.findAll().stream().findFirst().orElse(createDefaultParametrage());
    }

    @Transactional
    public void save(ParametrageGeneral param) {
        repository.save(param);
    }

    /**
     * Crée un paramétrage par défaut si aucun n'existe
     */
    private ParametrageGeneral createDefaultParametrage() {
        ParametrageGeneral defaultParam = new ParametrageGeneral();
        // Les valeurs par défaut sont définies dans le modèle
        return defaultParam;
    }

    /**
     * Obtient la durée de prêt pour un adhérent donné
     */
    public Integer getDureePretPourAdherent(Adherent adherent) {
        ParametrageGeneral param = getParametrage();
        return param.getDureePretParType(
            adherent.getEstEtudiant(), 
            adherent.getEstProfessionnel(), 
            adherent.getEstAnonyme()
        );
    }

    /**
     * Obtient le quota maximum pour un adhérent donné
     */
    public Integer getQuotaMaxPourAdherent(Adherent adherent) {
        ParametrageGeneral param = getParametrage();
        return param.getQuotaMaxParType(
            adherent.getEstEtudiant(), 
            adherent.getEstProfessionnel(), 
            adherent.getEstAnonyme()
        );
    }

    /**
     * Calcule la date de fin prévue pour un prêt selon le type d'adhérent
     */
    public LocalDate calculerDateFinPrevue(Adherent adherent) {
        Integer dureePret = getDureePretPourAdherent(adherent);
        return LocalDate.now().plusDays(dureePret);
    }

    /**
     * Calcule la date de fin prévue pour un prêt avec une date de début spécifique
     */
    public LocalDate calculerDateFinPrevue(Adherent adherent, LocalDate dateDebut) {
        Integer dureePret = getDureePretPourAdherent(adherent);
        return dateDebut.plusDays(dureePret);
    }

    /**
     * Obtient la durée de prolongation
     */
    public Integer getDureeProlongation() {
        ParametrageGeneral param = getParametrage();
        return param.getDureeProlongation();
    }

    /**
     * Obtient le nombre maximum de prolongations autorisées
     */
    public Integer getNombreProlongationsMax() {
        ParametrageGeneral param = getParametrage();
        return param.getNombreProlongationsMax();
    }

    /**
     * Met à jour le quota d'un adhérent selon son type
     */
    @Transactional
    public void mettreAJourQuotaAdherent(Adherent adherent) {
        Integer quotaMax = getQuotaMaxPourAdherent(adherent);
        adherent.setQuotaMax(quotaMax);
    }

    public Integer getJoursPenalitePourAdherent(Adherent adherent) {
        ParametrageGeneral param = getParametrage();
        if (adherent.getEstEtudiant() != null && adherent.getEstEtudiant()) {
            return param.getJoursPenaliteEtudiant();
        } else if (adherent.getEstProfessionnel() != null && adherent.getEstProfessionnel()) {
            return param.getJoursPenaliteProfessionnel();
        } else {
            return param.getJoursPenaliteEnseignant();
        }
    }
}