package com.bibliotheque.service;

import com.bibliotheque.model.ParametrageGeneral;
import com.bibliotheque.repository.ParametrageGeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametrageGeneralService {
    @Autowired
    private ParametrageGeneralRepository repository;

    public ParametrageGeneral getParametrage() {
        return repository.findAll().stream().findFirst().orElse(null);
    }

    public void save(ParametrageGeneral param) {
        repository.save(param);
    }
} 