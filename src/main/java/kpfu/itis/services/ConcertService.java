package kpfu.itis.services;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.Concert;

import java.sql.Date;
import java.util.Optional;

public interface ConcertService {
    Concert addConcert(ConcertForm concertForm);
    Long getIdByName(String name);
    void update(Long id, Date date, String name);
}
