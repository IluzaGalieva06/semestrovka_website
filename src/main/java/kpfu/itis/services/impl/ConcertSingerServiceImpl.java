package kpfu.itis.services.impl;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.ConcertSinger;
import kpfu.itis.models.Singer;
import kpfu.itis.repositories.ConcertRepository;
import kpfu.itis.repositories.ConcertSingerRepository;
import kpfu.itis.repositories.SingerRepository;
import kpfu.itis.services.ConcertSingerService;

import java.util.*;

public class ConcertSingerServiceImpl implements ConcertSingerService {
    private ConcertSingerRepository concertSingerRepository;
    private SingerRepository singerRepository;
    private ConcertRepository concertRepository;


    public ConcertSingerServiceImpl(ConcertSingerRepository concertSingerRepository, SingerRepository singerRepository, ConcertRepository concertRepository) {
        this.concertSingerRepository = concertSingerRepository;
        this.singerRepository = singerRepository;
        this.concertRepository = concertRepository;


    }
    @Override
    public void save(ConcertSinger concertSinger) {
        concertSingerRepository.save(concertSinger.getConcertId(),concertSinger.getSingerId());

    }
    @Override
    public List<String> getSingerNickname(Long id) {
        List<Long> singerIds = concertSingerRepository.findSingerIdsByConcertId(id.longValue());

        List<String> singerNames = new ArrayList<>();
        for (Long singerId : singerIds) {
            Optional<Singer> singerOptional = singerRepository.findById(singerId);
            if (singerOptional.isPresent()) {
                singerNames.add(singerOptional.get().getNickname());
            }
        }
        return singerNames;
    }
    @Override
    public List<ConcertForm> getConcertForm(){
        List<ConcertForm> concerts = concertRepository.getAllConcertForm();
        return concerts;
    }
    @Override
    public Map<Long, List<String>> getAllSingerFromConcertForm(){
        List<ConcertForm> concerts = getConcertForm();
        Map<Long, List<String>> concertSingerMap = new HashMap<>();

        for (ConcertForm concert : concerts) {
            List<Long> singerIds = concertSingerRepository.findSingerIdsByConcertId(concert.getId());
            List<String> singerNames = new ArrayList<>();

            for (Long singerId : singerIds) {
                Optional<Singer> singerOptional = singerRepository.findById(singerId);
                singerOptional.ifPresent(singer -> singerNames.add(singer.getNickname()));
            }

            concertSingerMap.put(concert.getId(), singerNames);
        }
        return concertSingerMap;

    }
    @Override
    public Singer getSingerByName(String name) {
        return singerRepository.findByName(name);
    }
}
