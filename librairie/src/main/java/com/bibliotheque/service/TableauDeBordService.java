package com.bibliotheque.service;

import com.bibliotheque.model.TableauDeBord;
import com.bibliotheque.repository.TableauDeBordRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.PenaliteRepository;
import com.bibliotheque.repository.AdherentRepository;
import com.bibliotheque.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TableauDeBordService {
    @Autowired
    private TableauDeBordRepository tableauDeBordRepository;
    @Autowired
    private PretRepository pretRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PenaliteRepository penaliteRepository;
    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private LivreRepository livreRepository;

    public Optional<TableauDeBord> findByDate(LocalDate date) {
        return tableauDeBordRepository.findAll().stream().filter(t -> t.getDateStat().equals(date)).findFirst();
    }

    public TableauDeBord save(TableauDeBord tableau) {
        return tableauDeBordRepository.save(tableau);
    }
    public TableauDeBord getStatistiquesPourDate(LocalDate date) {
        TableauDeBord stats = new TableauDeBord();
        stats.setDateStat(date);
        stats.setNombrePrets(pretRepository.countByDateDebut(date));
        stats.setNombreRetours(pretRepository.countByDateRenduReelle(date));
        stats.setNombreReservations(reservationRepository.countByDateReservation(date));
        stats.setNombrePenalites(penaliteRepository.countByDateDebut(date));
        stats.setNombreAdherentsActifs(Long.valueOf(adherentRepository.count()).intValue());
        stats.setNombreLivresDisponibles(Long.valueOf(livreRepository.count()).intValue());
        return stats;
    }
} 