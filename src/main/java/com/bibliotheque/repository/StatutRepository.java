package com.bibliotheque.repository;

import com.bibliotheque.model.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutRepository extends JpaRepository<Statut, Integer> {
} 