package kpfu.itis.repositories;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.Concert;
import kpfu.itis.repositories.generic.CrudRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ConcertRepository extends CrudRepository<Concert> {
    Optional<Concert> findByName(String model);

    Concert saveStart(ConcertForm model);

    void updatePosterForConcert(String name, Long fileId);

     List<ConcertForm> getAllConcertForm();
    Optional<ConcertForm> findByNameConcertForm(String name);
    Optional<ConcertForm> findByIdConcertForm(Long id);
    boolean deleteBoolean(Long id);
    void update(Long id, Date date, String name);
}




