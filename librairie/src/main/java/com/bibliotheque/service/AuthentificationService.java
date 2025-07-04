package com.bibliotheque.service;

import com.bibliotheque.model.Administrateur;
import com.bibliotheque.model.Adherent;
import com.bibliotheque.repository.AdministrateurRepository;
import com.bibliotheque.repository.AdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthentificationService {
    
    @Autowired
    private AdministrateurRepository administrateurRepository;
    
    @Autowired
    private AdherentRepository adherentRepository;
    
    public Optional<Administrateur> authentifierAdmin(String login, String motDePasse) {
        return administrateurRepository.findByLoginAndMotDePasse(login, motDePasse);
    }
    
    public Optional<Adherent> authentifierAdherent(String login, String motDePasse) {
        try {
            System.out.println("Recherche adhérent avec login: " + login);
            var result = adherentRepository.findByLoginAndMotDePasse(login, motDePasse);
            System.out.println("Résultat de la recherche: " + (result.isPresent() ? "Trouvé" : "Non trouvé"));
            return result;
        } catch (Exception e) {
            System.err.println("Erreur dans authentifierAdherent: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
} 