package kpfu.itis.services;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.ConcertSinger;
import kpfu.itis.models.Singer;

import java.util.List;
import java.util.Map;

public interface ConcertSingerService {
    void save(ConcertSinger concertSinger);
    List<String> getSingerNickname(Long id);
    Map<Long, List<String>> getAllSingerFromConcertForm();
    List<ConcertForm> getConcertForm();

     Singer getSingerByName(String name);

}
