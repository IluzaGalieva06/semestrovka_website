package kpfu.itis.services;

import kpfu.itis.dto.TicketForm;

import java.util.List;

public interface TicketService {
    List<TicketForm> getTicketDetails(Long id);
    void addTicketToConcert( TicketForm ticketForm);
}
