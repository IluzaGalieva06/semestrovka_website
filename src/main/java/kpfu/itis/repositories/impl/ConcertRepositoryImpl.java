package kpfu.itis.repositories.impl;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.Concert;

import kpfu.itis.repositories.ConcertRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConcertRepositoryImpl implements ConcertRepository {
    private static final String SQL_GET_BY_NAME = "select * from concert where LOWER(name) = LOWER(?)";
    private static final String SQL_SELECT_ALL = "select * from concert";
    private static final String SQL_INSERT_CONCERT = "INSERT INTO concert ( name, date, description, poster_id, location_id, price) VALUES ( ?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_LOCATION_ID = "SELECT id FROM location WHERE location.location = ?";

    private final static String SQL_UPDATE_AVATAR = "update concert set poster_id = ? where name = ?";
    private final static String SQL_UPDATE_DATE_AND_NAME = "update concert set name = ?, date = ? WHERE id = ?";


    private static final String SQL_GET_BY_ID = "select * from concert where id = ?";
    private static final String SQL_GET_CONCERT_FORM = "SELECT concert.id, concert.name, location.location as location,  concert.date, concert.description, concert.poster_id, concert.price FROM concert  JOIN location ON concert.location_id = location.id;";

    private static final String SQL_GET_BY_NAME_CONCERT_FORM = "SELECT concert.id, concert.name, location.location as location, concert.date, concert.description, concert.poster_id, concert.price FROM concert  JOIN location ON concert.location_id = location.id where LOWER(concert.name) = LOWER(?);";
    private static final String SQL_GET_BY_ID_CONCERT_FORM = "SELECT concert.id, concert.name, location.location as location,  concert.date, concert.description, concert.poster_id, concert.price FROM concert  JOIN location ON concert.location_id = location.id where concert.id = ?;";
    private static final String SQL_DELETE_BY_ID = "delete from concert where id = ?";



    private Connection connection;


    public ConcertRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void update(Long id, Date date, String name){
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DATE_AND_NAME);
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, date);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Concert saveStart(ConcertForm model) {
        try {


            PreparedStatement getLocationIdStatement = connection.prepareStatement(SQL_GET_LOCATION_ID);
            getLocationIdStatement.setString(1, model.getLocation());
            ResultSet locationIdResult = getLocationIdStatement.executeQuery();

            Long locationId;
            if (locationIdResult.next()) {
                locationId = locationIdResult.getLong("id");
            } else {
                throw new SQLException("Failed to get location id, no keys generated.");
            }


            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CONCERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, model.getName());
            preparedStatement.setDate(2, model.getDate());
            preparedStatement.setString(3, model.getDescription());
            if (model.getPosterId() != null) {
                preparedStatement.setLong(4, model.getPosterId());
            } else {
                preparedStatement.setNull(4, Types.NULL);
            }
            preparedStatement.setLong(5, locationId);
            preparedStatement.setLong(6, model.getPrice());

            preparedStatement.executeUpdate();

            Concert concert = new Concert();
            concert.setName(model.getName());
            concert.setLocationId(locationId);
            concert.setDate(model.getDate());
            concert.setDescription(model.getDescription());
            concert.setPosterId(model.getPosterId());
            concert.setPrice(model.getPrice());

            try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    model.setId(generatedIds.getLong("id"));
                    concert.setId(generatedIds.getLong("id"));
                    return concert;

                } else {
                    throw new SQLException("Cannot retrieve id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void updatePosterForConcert(String name, Long fileId) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_AVATAR);
            preparedStatement.setLong(1, fileId);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ConcertForm> getAllConcertForm() {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_CONCERT_FORM);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<ConcertForm> result = new ArrayList<>();
            while (resultSet.next()) {
                ConcertForm concertForm = ConcertForm.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .location(resultSet.getString("location"))
                        .date(resultSet.getDate("date"))
                        .description(resultSet.getString("description"))
                        .posterId(resultSet.getLong("poster_id"))
                        .price(resultSet.getLong("price"))
                        .build();
                result.add(concertForm);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Concert save(Concert model) {
        return null;
    }

    @Override
    public List<Concert> getAll() {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Concert> result = new ArrayList<>();
            while (resultSet.next()) {
                Concert concert = Concert.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .locationId(resultSet.getLong("location_id"))
                        .date(resultSet.getDate("date"))
                        .description(resultSet.getString("description"))
                        .posterId(resultSet.getLong("poster_id"))
                        .price(resultSet.getLong("price"))
                        .build();
                result.add(concert);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);

            preparedStatement.setLong(1, id);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new SQLException("Cannot delete account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean deleteBoolean(Long id) {
        boolean isDeleted = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
            preparedStatement.setLong(1, id);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new SQLException("Cannot delete account");
            }
            isDeleted = true; // Установить значение в true, если удаление прошло успешно
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public Optional<Concert> findById(Long id) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID);

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() == false) {
                    return Optional.empty();
                }

                Concert concert = Concert.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .locationId(resultSet.getLong("location_id"))
                        .date(resultSet.getDate("date"))
                        .description(resultSet.getString("description"))
                        .posterId(resultSet.getLong("poster_id") == 0 ? null : resultSet.getLong("poster_id"))
                        .price(resultSet.getLong("price"))
                        .build();
                return Optional.of(concert);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ConcertForm> findByIdConcertForm(Long id) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID_CONCERT_FORM);

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() == false) {
                    return Optional.empty();
                }
                ConcertForm concertForm = ConcertForm.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .location(resultSet.getString("location"))
                        .date(resultSet.getDate("date"))
                        .description(resultSet.getString("description"))
                        .posterId(resultSet.getLong("poster_id"))
                        .price(resultSet.getLong("price"))
                        .build();
                return Optional.of(concertForm);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<ConcertForm> findByNameConcertForm(String name) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_NAME_CONCERT_FORM);

            preparedStatement.setString(1, name.toLowerCase());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() == false) {
                    return Optional.empty();
                }
                ConcertForm concertForm = ConcertForm.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .location(resultSet.getString("location"))
                        .date(resultSet.getDate("date"))
                        .description(resultSet.getString("description"))
                        .posterId(resultSet.getLong("poster_id"))
                        .price(resultSet.getLong("price"))
                        .build();
                return Optional.of(concertForm);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Concert> findByName(String name) {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_NAME);

            preparedStatement.setString(1, name.toLowerCase());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() == false) {
                return Optional.empty();
            }
            Concert concert = Concert.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .locationId(resultSet.getLong("location_id"))
                    .date(resultSet.getDate("date"))
                    .description(resultSet.getString("description"))
                    .posterId(resultSet.getLong("poster_id") == 0 ? null : resultSet.getLong("poster_id"))
                    .price(resultSet.getLong("price"))
                    .build();

            return Optional.of(concert);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
