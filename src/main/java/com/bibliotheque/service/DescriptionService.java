package com.bibliotheque.service;

import com.bibliotheque.model.Description;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DescriptionService {
    @Autowired
    private DescriptionRepository descriptionRepository;

    public List<Description> findAll() {
        return descriptionRepository.findAll();
    }

    public Optional<Description> findById(Integer id) {
        return descriptionRepository.findById(id);
    }

    public Optional<Description> findByLivre(Livre livre) {
        return descriptionRepository.findAll().stream().filter(d -> d.getLivre().equals(livre)).findFirst();
    }

    public Description save(Description description) {
        return descriptionRepository.save(description);
    }
} 