package kpfu.itis.services.impl;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.Concert;
import kpfu.itis.repositories.ConcertRepository;
import kpfu.itis.services.ConcertService;

import java.sql.Date;
import java.util.Optional;

public class ConcertServiceImpl implements ConcertService {
    private ConcertRepository concertRepository;

    public ConcertServiceImpl(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @Override
    public Concert addConcert(ConcertForm concertForm) {

        Concert savedConcert = concertRepository.saveStart(concertForm);


        return savedConcert;
    }

    @Override
    public Long getIdByName(String name) {
        Optional<Concert> concert = concertRepository.findByName(name);
        return concert.get().getId();
    }
    @Override
    public void update(Long id, Date date, String name){
        concertRepository.update(id, date,name);

    }
}
