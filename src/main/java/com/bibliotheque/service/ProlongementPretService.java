package com.bibliotheque.service;

import com.bibliotheque.model.ProlongementPret;
import com.bibliotheque.repository.ProlongementPretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProlongementPretService {
    @Autowired
    private ProlongementPretRepository prolongementPretRepository;

    public List<ProlongementPret> findAll() {
        return prolongementPretRepository.findAll();
    }

    public Optional<ProlongementPret> findById(Integer id) {
        return prolongementPretRepository.findById(id);
    }

    public ProlongementPret save(ProlongementPret prolongementPret) {
        return prolongementPretRepository.save(prolongementPret);
    }
} 