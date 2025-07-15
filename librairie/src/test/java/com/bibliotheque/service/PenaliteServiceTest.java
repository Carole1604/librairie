package com.bibliotheque.service;

import com.bibliotheque.model.Penalite;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Adherent;
import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.PenaliteRepository;
import com.bibliotheque.repository.JourFerieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PenaliteServiceTest {

    @Mock
    private PenaliteRepository penaliteRepository;

    @Mock
    private JourFerieRepository jourFerieRepository;

    @InjectMocks
    private PenaliteService penaliteService;

    private Pret pret;
    private Adherent adherent;
    private Exemplaire exemplaire;
    private Livre livre;

    @BeforeEach
    void setUp() {
        // Créer les objets de test
        adherent = new Adherent();
        adherent.setId(1);
        adherent.setNom("Dupont");
        adherent.setPrenom("Jean");

        livre = new Livre();
        livre.setId(1);
        livre.setTitre("Test Book");

        exemplaire = new Exemplaire();
        exemplaire.setId(1);
        exemplaire.setLivre(livre);

        pret = new Pret();
        pret.setId(1);
        pret.setAdherent(adherent);
        pret.setExemplaire(exemplaire);
        pret.setDateDebut(LocalDate.now().minusDays(10));
        pret.setDateFinPrevue(LocalDate.now().minusDays(2));
        pret.setDateRenduReelle(LocalDate.now());
    }

    @Test
    void testCalculerJoursRetard_SansRetard() {
        // Arrange
        LocalDate dateFinPrevue = LocalDate.now();
        LocalDate dateRetourReelle = LocalDate.now().minusDays(1);

        // Act
        int joursRetard = penaliteService.calculerJoursRetard(dateFinPrevue, dateRetourReelle);

        // Assert
        assertEquals(0, joursRetard);
    }

    @Test
    void testCalculerJoursRetard_AvecRetard() {
        // Arrange
        LocalDate dateFinPrevue = LocalDate.now().minusDays(3);
        LocalDate dateRetourReelle = LocalDate.now();

        // Act
        int joursRetard = penaliteService.calculerJoursRetard(dateFinPrevue, dateRetourReelle);

        // Assert
        assertTrue(joursRetard > 0);
        assertTrue(joursRetard <= 3); // Ne peut pas dépasser le nombre de jours de retard
    }

    @Test
    void testCreerPenaliteAutomatique_SansRetard() {
        // Arrange
        pret.setDateRenduReelle(pret.getDateFinPrevue().minusDays(1)); // Retour avant la date prévue

        // Act
        Penalite penalite = penaliteService.creerPenaliteAutomatique(pret);

        // Assert
        assertNull(penalite);
    }

    @Test
    void testCreerPenaliteAutomatique_AvecRetard() {
        // Arrange
        pret.setDateRenduReelle(LocalDate.now()); // Retour après la date prévue
        when(penaliteRepository.save(any(Penalite.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Penalite penalite = penaliteService.creerPenaliteAutomatique(pret);

        // Assert
        assertNotNull(penalite);
        assertEquals(adherent, penalite.getAdherent());
        assertEquals(pret, penalite.getPret());
        assertTrue(penalite.getEstActive());
        assertNotNull(penalite.getDateCreation());
        assertTrue(penalite.getNombreJoursRetard() > 0);
        
        verify(penaliteRepository).save(penalite);
    }

    @Test
    void testCreerPenaliteAutomatique_DatesManquantes() {
        // Arrange
        pret.setDateRenduReelle(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            penaliteService.creerPenaliteAutomatique(pret);
        });
    }
} 