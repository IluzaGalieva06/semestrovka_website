package kpfu.itis.repositories.generic;


import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T save(T model);

    List<T> getAll();

    void delete(Long id);

    Optional<T> findById(Long id);





}

