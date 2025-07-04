package com.bibliotheque.service;

import com.bibliotheque.model.Penalite;
import com.bibliotheque.repository.PenaliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PenaliteService {
    @Autowired
    private PenaliteRepository penaliteRepository;

    public List<Penalite> findAll() {
        return penaliteRepository.findAll();
    }

    public Optional<Penalite> findById(Integer id) {
        return penaliteRepository.findById(id);
    }

    public Penalite save(Penalite penalite) {
        return penaliteRepository.save(penalite);
    }
} 