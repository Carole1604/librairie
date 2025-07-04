package com.bibliotheque.repository;

import com.bibliotheque.model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    int countByDateDebut(LocalDate dateDebut);
    int countByDateRenduReelle(LocalDate dateRenduReelle);
} 