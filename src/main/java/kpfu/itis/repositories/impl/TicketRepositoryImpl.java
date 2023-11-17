package kpfu.itis.repositories.impl;

import kpfu.itis.dto.TicketForm;
import kpfu.itis.models.Ticket;
import kpfu.itis.repositories.TicketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class TicketRepositoryImpl implements TicketRepository {
    private static final String SQL_SAVE = "insert into ticket(concert_id,user_id,price) values(?, ?, ?)";
    private static final String SQL_GET_CONCERT_NAME = "SELECT id FROM concert WHERE concert.name = ?";
    private static final String SQL_GET_CLIENT_ID = "SELECT id FROM client WHERE client.email = ?";
    private static final String SQL_GET_ALL_TICKETS = "SELECT ticket.id, concert.name as concert_name, ticket.user_id, ticket.price FROM ticket JOIN concert ON ticket.concert_id = concert.id  WHERE user_id = ?";
    private static final String SQL_DELETE_BY_ID = "delete from ticket where  concert_id = ?";



    private final Connection connection;

    public TicketRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Ticket save(TicketForm model) {
        try {
            PreparedStatement getConcertIdStatement = connection.prepareStatement(SQL_GET_CONCERT_NAME);
            getConcertIdStatement.setString(1, model.getConcertName());
            ResultSet concertIdResult = getConcertIdStatement.executeQuery();

            Long concertId;
            if (concertIdResult.next()) {
                concertId = concertIdResult.getLong("id");
            } else {
                throw new SQLException("Failed to get concert id, no keys generated.");
            }

            PreparedStatement getClientIdStatement = connection.prepareStatement(SQL_GET_CLIENT_ID);
            getClientIdStatement.setString(1, model.getUserEmail());
            ResultSet clientIdResult = getClientIdStatement.executeQuery();

            Long clientId;
            if (clientIdResult.next()) {
                clientId = clientIdResult.getLong("id");
            } else {
                throw new SQLException("Failed to get client id, no keys generated.");
            }


            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, concertId);
            preparedStatement.setLong(2, clientId);
            preparedStatement.setLong(3, model.getPrice());

            preparedStatement.executeUpdate();

            Ticket ticket = new Ticket();
            ticket.setConcertId(concertId);
            ticket.setUserId(clientId);
            ticket.setPrice(model.getPrice());


            try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    model.setId(generatedIds.getLong("id"));
                    ticket.setId(generatedIds.getLong("id"));
                    return ticket;

                } else {
                    throw new SQLException("Cannot retrieve id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<TicketForm> getTicketsByUser(Long userId) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_TICKETS);

            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<TicketForm> tickets = new ArrayList<>();
            while (resultSet.next()) {
                TicketForm ticketForm = TicketForm.builder()
                        .id(resultSet.getLong("id"))
                        .concertName(resultSet.getString("concert_name"))
                        .userEmail(resultSet.getString("user_id"))
                        .price(resultSet.getLong("price"))
                        .build();

                tickets.add(ticketForm);
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ticket save(Ticket model) {
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);

            preparedStatement.setLong(1, id);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new SQLException("Cannot delete ticket");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return Optional.empty();
    }
}
