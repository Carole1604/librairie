package com.bibliotheque.service;

import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExemplaireService {
    @Autowired
    private ExemplaireRepository exemplaireRepository;

    public List<Exemplaire> findAll() {
        return exemplaireRepository.findAll();
    }

    public List<Exemplaire> findByLivre(Livre livre) {
        return exemplaireRepository.findByLivre(livre);
    }

    public Optional<Exemplaire> findById(Integer id) {
        return exemplaireRepository.findById(id);
    }

    public Exemplaire save(Exemplaire exemplaire) {
        return exemplaireRepository.save(exemplaire);
    }

    public void deleteById(Integer id) {
        exemplaireRepository.deleteById(id);
    }
} 