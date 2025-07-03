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

    public List<Livre> rechercheLivres(String titre, String auteur, String langue, String categorie) {
        return livreRepository.findAll().stream()
            .filter(l -> (titre == null || l.getTitre().toLowerCase().contains(titre.toLowerCase())))
            .filter(l -> (auteur == null || l.getAuteur().toLowerCase().contains(auteur.toLowerCase())))
            .filter(l -> {
                Description d = descriptionRepository.findAll().stream().filter(desc -> desc.getLivre().equals(l)).findFirst().orElse(null);
                return (langue == null || (d != null && d.getLangue() != null && d.getLangue().toLowerCase().contains(langue.toLowerCase()))) &&
                       (categorie == null || (d != null && d.getCategorie() != null && d.getCategorie().toLowerCase().contains(categorie.toLowerCase())));
            })
            .collect(Collectors.toList());
    }
} 