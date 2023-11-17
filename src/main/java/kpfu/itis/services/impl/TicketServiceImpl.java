package kpfu.itis.services.impl;

import kpfu.itis.dto.TicketForm;
import kpfu.itis.repositories.TicketRepository;
import kpfu.itis.services.TicketService;

import java.util.List;

public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }



    @Override
    public void addTicketToConcert(TicketForm ticketForm) {

        ticketRepository.save(ticketForm);
    }
    @Override
    public List<TicketForm> getTicketDetails(Long id) {
        List<TicketForm> tickets = ticketRepository.getTicketsByUser(id);
        return tickets;
    }
}



