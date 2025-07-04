package com.bibliotheque.repository;

import com.bibliotheque.model.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdherentRepository extends JpaRepository<Adherent, Integer> {
    Optional<Adherent> findByLogin(String login);
    
    @Query("SELECT a FROM Adherent a WHERE a.login = :login AND a.motDePasse = :motDePasse")
    Optional<Adherent> findByLoginAndMotDePasse(@Param("login") String login, @Param("motDePasse") String motDePasse);
} 