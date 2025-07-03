package com.bibliotheque.service;

import com.bibliotheque.model.TableauDeBord;
import com.bibliotheque.repository.TableauDeBordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TableauDeBordService {
    @Autowired
    private TableauDeBordRepository tableauDeBordRepository;

    public Optional<TableauDeBord> findByDate(LocalDate date) {
        return tableauDeBordRepository.findAll().stream().filter(t -> t.getDateStat().equals(date)).findFirst();
    }

    public TableauDeBord save(TableauDeBord tableau) {
        return tableauDeBordRepository.save(tableau);
    }
} 