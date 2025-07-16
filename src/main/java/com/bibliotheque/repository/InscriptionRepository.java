 package com.bibliotheque.repository;

import com.bibliotheque.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {
    Inscription findByAdherent_Id(Integer adherentId);
}