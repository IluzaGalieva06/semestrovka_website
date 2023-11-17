package kpfu.itis.services.impl;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.Concert;
import kpfu.itis.repositories.ConcertRepository;
import kpfu.itis.services.SearchService;

import java.util.Optional;

public class SearchServiceImpl implements SearchService {
    private ConcertRepository concertRepository;

    public SearchServiceImpl(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }


    @Override
    public Optional<ConcertForm> search(String name) {
        return concertRepository.findByNameConcertForm(name);
    }

    @Override
    public Optional<ConcertForm> searchId(Long id) {
        return concertRepository.findByIdConcertForm(id);
    }

    @Override
    public Long getId(String name) {
        Optional<Concert> concertOptional = concertRepository.findByName(name);
        return concertOptional.get().getId();

    }
}
