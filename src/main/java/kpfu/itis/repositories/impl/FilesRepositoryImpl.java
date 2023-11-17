package kpfu.itis.repositories.impl;

import kpfu.itis.models.FileInfo;
import kpfu.itis.repositories.FilesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilesRepositoryImpl implements FilesRepository {

    private final static String SQL_INSERT = "insert into file_info(storage_file_name, original_file_name, type, size) " +
            "values (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "update file_info set storage_file_name = ?, original_file_name = ?, type = ?, size = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select * from file_info where id = ?";
    private final static String SQL_SELECT_ALL = "select * from file_info";

    private final Connection connection;



    public FilesRepositoryImpl(Connection connection) {
        this.connection = connection;

    }


    @Override
    public FileInfo save(FileInfo entity) {
        if (entity.getId() == null) {
            try {

                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, entity.getStorageFileName());
                preparedStatement.setString(2, entity.getOriginalFileName());
                preparedStatement.setString(3, entity.getType());
                preparedStatement.setLong(4, entity.getSize());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Inserting FileInfo failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Inserting FileInfo failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
                preparedStatement.setString(1, entity.getStorageFileName());
                preparedStatement.setString(2, entity.getOriginalFileName());
                preparedStatement.setString(3, entity.getType());
                preparedStatement.setLong(4, entity.getSize());
                preparedStatement.setLong(5, entity.getId());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating FileInfo failed, no rows affected.");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return entity;
    }

    @Override
    public Optional<FileInfo> findById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                FileInfo fileInfo = FileInfo.builder()
                        .id(resultSet.getLong("id"))
                        .originalFileName(resultSet.getString("original_file_name"))
                        .storageFileName(resultSet.getString("storage_file_name"))
                        .size(resultSet.getLong("size"))
                        .type(resultSet.getString("type"))
                        .build();
                return Optional.of(fileInfo);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public List<FileInfo> getAll() {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<FileInfo> result = new ArrayList<>();
            while (resultSet.next()) {
                FileInfo fileInfo = FileInfo.builder()
                        .id(resultSet.getLong("id"))
                        .originalFileName(resultSet.getString("original_file_name"))
                        .storageFileName(resultSet.getString("storage_file_name"))
                        .size(resultSet.getLong("size"))
                        .type(resultSet.getString("type"))
                        .build();
                result.add(fileInfo);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Long id) {

    }


}
