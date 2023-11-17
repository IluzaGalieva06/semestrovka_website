package kpfu.itis.repositories.impl;

import kpfu.itis.models.ConcertSinger;
import kpfu.itis.repositories.ConcertSingerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConcertSingerRepositoryImpl implements ConcertSingerRepository {
    final private static String SQL_SAVE = "INSERT INTO concert_singer (concert_id, singer_id) VALUES (?, ?)";
    final private static String SQL_FIND_SINGER_IDS_BY_CONCERT_ID = "SELECT singer_id FROM concert_singer WHERE concert_id = ?";
    private static final String SQL_DELETE_BY_ID = "delete from concert_singer where concert_id = ?";



    private final Connection connection;

    public ConcertSingerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ConcertSinger save(ConcertSinger model) {
        return null;
    }

    @Override
    public List<ConcertSinger> getAll() {
        return null;
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
    public Optional<ConcertSinger> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Long concertId, Long singerId) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE);

            preparedStatement.setLong(1, concertId);
            preparedStatement.setLong(2, singerId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Long> findSingerIdsByConcertId(Long concertId) {
        try {
            List<Long> singerIds = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_SINGER_IDS_BY_CONCERT_ID);
            statement.setLong(1, concertId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long singerId = resultSet.getLong("singer_id");
                    singerIds.add(singerId);
                }
                return singerIds;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
