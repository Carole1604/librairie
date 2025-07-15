package com.bibliotheque.service;

import com.bibliotheque.model.Adherent;
import com.bibliotheque.repository.AdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;
    
    @Autowired
    private ParametrageGeneralService parametrageService;

    public List<Adherent> findAll() {
        return adherentRepository.findAll();
    }

    public Optional<Adherent> findById(Integer id) {
        return adherentRepository.findById(id);
    }

    @Transactional
    public Adherent save(Adherent adherent) {
        // Si c'est un nouvel adhérent (pas d'ID), définir le quota selon son type
        if (adherent.getId() == null) {
            Integer quotaMax = parametrageService.getQuotaMaxPourAdherent(adherent);
            adherent.setQuotaMax(quotaMax);
            adherent.setQuotaActuel(0); // Nouvel adhérent, pas d'emprunts
        }
        return adherentRepository.save(adherent);
    }

    public void deleteById(Integer id) {
        adherentRepository.deleteById(id);
    }
    
    /**
     * Met à jour le quota d'un adhérent selon son type
     */
    @Transactional
    public void mettreAJourQuotaAdherent(Adherent adherent) {
        Integer quotaMax = parametrageService.getQuotaMaxPourAdherent(adherent);
        adherent.setQuotaMax(quotaMax);
        adherentRepository.save(adherent);
    }
    
    /**
     * Met à jour les quotas de tous les adhérents selon leur type
     */
    @Transactional
    public void mettreAJourQuotasTousAdherents() {
        List<Adherent> adherents = findAll();
        for (Adherent adherent : adherents) {
            mettreAJourQuotaAdherent(adherent);
        }
    }
    
    /**
     * Incrémente le quota actuel d'un adhérent (lors d'un emprunt)
     */
    @Transactional
    public void incrementerQuotaActuel(Adherent adherent) {
        adherent.setQuotaActuel(adherent.getQuotaActuel() + 1);
        adherentRepository.save(adherent);
    }
    
    /**
     * Décrémente le quota actuel d'un adhérent (lors d'un retour)
     */
    @Transactional
    public void decrementerQuotaActuel(Adherent adherent) {
        if (adherent.getQuotaActuel() > 0) {
            adherent.setQuotaActuel(adherent.getQuotaActuel() - 1);
            adherentRepository.save(adherent);
        }
    }
} 