package com.bibliotheque.service;

import com.bibliotheque.model.JourFerie;
import com.bibliotheque.repository.JourFerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourFerieService {
    @Autowired
    private JourFerieRepository jourFerieRepository;

    public List<JourFerie> findAll() {
        return jourFerieRepository.findAll();
    }

    public Optional<JourFerie> findById(Integer id) {
        return jourFerieRepository.findById(id);
    }

    public JourFerie save(JourFerie jourFerie) {
        return jourFerieRepository.save(jourFerie);
    }

    public void deleteById(Integer id) {
        jourFerieRepository.deleteById(id);
    }
} 