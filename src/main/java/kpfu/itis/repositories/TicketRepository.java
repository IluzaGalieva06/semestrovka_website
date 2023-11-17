package kpfu.itis.repositories;

import kpfu.itis.dto.TicketForm;
import kpfu.itis.models.Ticket;
import kpfu.itis.repositories.generic.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket> {
    Ticket save(TicketForm model);
    List<TicketForm> getTicketsByUser(Long userId);
}
