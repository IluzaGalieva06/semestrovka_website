package kpfu.itis.repositories;

import kpfu.itis.models.ConcertSinger;
import kpfu.itis.repositories.generic.CrudRepository;

import java.util.List;

public interface ConcertSingerRepository extends CrudRepository<ConcertSinger> {
    void save(Long concertId, Long singerId);
    List<Long> findSingerIdsByConcertId(Long concertId);
}
