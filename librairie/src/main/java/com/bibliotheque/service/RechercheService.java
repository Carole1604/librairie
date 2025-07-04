package com.bibliotheque.service;

import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Description;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RechercheService {
    @Autowired
    private LivreRepository livreRepository;
    @Autowired
    private DescriptionRepository descriptionRepository;

    public static class LivreDescriptionDTO {
        public final Livre livre;
        public final String langue;
        public final String categorie;
        public LivreDescriptionDTO(Livre livre, String langue, String categorie) {
            this.livre = livre;
            this.langue = langue;
            this.categorie = categorie;
        }
    }

    public List<LivreDescriptionDTO> rechercheLivres(String titre, String auteur, String langue, String categorie) {
        return livreRepository.findAll().stream()
            .filter(l -> (titre == null || l.getTitre().toLowerCase().contains(titre.toLowerCase())))
            .filter(l -> (auteur == null || l.getAuteur().toLowerCase().contains(auteur.toLowerCase())))
            .map(l -> {
                Description d = descriptionRepository.findAll().stream().filter(desc -> desc.getLivre().equals(l)).findFirst().orElse(null);
                return new LivreDescriptionDTO(l, d != null ? d.getLangue() : null, d != null ? d.getCategorie() : null);
            })
            .filter(dto -> (langue == null || (dto.langue != null && dto.langue.toLowerCase().contains(langue.toLowerCase())))
                       && (categorie == null || (dto.categorie != null && dto.categorie.toLowerCase().contains(categorie.toLowerCase()))))
            .collect(Collectors.toList());
    }
} 