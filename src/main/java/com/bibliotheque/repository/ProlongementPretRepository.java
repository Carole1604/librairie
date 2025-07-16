package com.bibliotheque.repository;

import com.bibliotheque.model.ProlongementPret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProlongementPretRepository extends JpaRepository<ProlongementPret, Integer> {
} 