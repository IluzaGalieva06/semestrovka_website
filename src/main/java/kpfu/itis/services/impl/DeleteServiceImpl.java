package kpfu.itis.services.impl;

import kpfu.itis.repositories.ConcertRepository;
import kpfu.itis.repositories.ConcertSingerRepository;
import kpfu.itis.repositories.SingerRepository;
import kpfu.itis.repositories.TicketRepository;
import kpfu.itis.services.DeleteService;

public class DeleteServiceImpl implements DeleteService {
    private ConcertRepository concertRepository;
    private TicketRepository ticketRepository;
    private ConcertSingerRepository concertSingerRepository;

    public DeleteServiceImpl(ConcertSingerRepository concertSingerRepository, TicketRepository ticketRepository, ConcertRepository concertRepository) {
        this.concertSingerRepository = concertSingerRepository;
        this.ticketRepository = ticketRepository;
        this.concertRepository = concertRepository;

    }
    public boolean deleteConcert(Long id){
        boolean isDelete = concertRepository.deleteBoolean(id);
        return isDelete;

    }
    public void deleteTicket(Long id){
        ticketRepository.delete(id);
    }
    public void deleteConcertSinger(Long id){
        concertSingerRepository.delete(id);
    }

}
