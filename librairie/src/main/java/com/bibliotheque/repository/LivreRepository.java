package com.bibliotheque.repository;

import com.bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre, Integer> {
} 