package com.bibliotheque.repository;

import com.bibliotheque.model.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Integer> {
    Optional<Administrateur> findByLogin(String login);
    Optional<Administrateur> findByLoginAndMotDePasse(String login, String motDePasse);
} 