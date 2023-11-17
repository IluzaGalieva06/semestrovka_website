package kpfu.itis.services;

import kpfu.itis.dto.ConcertForm;


import java.util.Optional;

public interface SearchService {
    Optional<ConcertForm> search(String name);
    Optional<ConcertForm> searchId(Long id);
    Long getId(String name);
}
