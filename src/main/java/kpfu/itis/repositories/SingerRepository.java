package kpfu.itis.repositories;

import kpfu.itis.models.Singer;
import kpfu.itis.repositories.generic.CrudRepository;

public interface SingerRepository extends CrudRepository<Singer> {
    Singer findByName(String singerName);
}
