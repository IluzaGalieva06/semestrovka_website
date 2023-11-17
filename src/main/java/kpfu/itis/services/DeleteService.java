package kpfu.itis.services;

public interface DeleteService {
    boolean deleteConcert(Long id);
    void deleteConcertSinger(Long id);
    void deleteTicket(Long id);
}
