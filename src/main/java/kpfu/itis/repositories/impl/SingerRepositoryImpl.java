package kpfu.itis.repositories.impl;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.Singer;
import kpfu.itis.models.User;
import kpfu.itis.repositories.SingerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SingerRepositoryImpl implements SingerRepository {
    private static final String SQL_GET_BY_NAME = "select * from singer where LOWER(nickname) = LOWER(?)";
    private static final String SQL_GET_BY_ID = "select * from singer where id = ?";


    private Connection connection;


    public SingerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Singer save(Singer model) {
        return null;
    }

    @Override
    public List<Singer> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Singer> findById(Long id) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID);

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                Singer singer = Singer.builder()
                        .id(resultSet.getLong("id"))
                        .nickname(resultSet.getString("nickname"))
                        .description(resultSet.getString("description"))
                        .build();
                return Optional.of(singer);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Singer findByName(String singerName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_NAME);

            preparedStatement.setString(1, singerName.toLowerCase());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() == false) {
                    return null;
                }
                Singer singer = Singer.builder()
                        .id(resultSet.getLong("id"))
                        .nickname(resultSet.getString("nickname"))
                        .description(resultSet.getString("description"))
                        .build();

                return singer;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
