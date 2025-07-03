package com.bibliotheque.repository;

import com.bibliotheque.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Integer> {
} 