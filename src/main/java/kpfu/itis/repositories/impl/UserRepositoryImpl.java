package kpfu.itis.repositories.impl;


import kpfu.itis.models.User;
import kpfu.itis.repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_SAVE = "insert into client(surname,username,email,password,avatar_id, userrole) values(?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL = "select * from client";

    private static final String SQL_GET_BY_ID = "select * from client where id = ?";

    private static final String SQL_GET_BY_USERNAME = "select * from client where username = ?";

    private static final String SQL_GET_BY_EMAIL = "select * from client where email = ?";

    private static final String SQL_DELETE_BY_ID = "delete from client where id = ?";
    private final static String SQL_UPDATE_AVATAR = "update client set avatar_id = ? where email = ?";
    private final static String SQL_UPDATE = "update client set surname = ?, username = ?, email = ?, password = ?, avatar_id = ?, userrole = ? where id = ?";

    private final Connection connection;


    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User save(User model) {
        if (model.getId() == null) {
            try {

                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, PreparedStatement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, model.getSurname());
                preparedStatement.setString(2, model.getUsername());
                preparedStatement.setString(3, model.getEmail());
                preparedStatement.setString(4, model.getPassword());
                if (model.getAvatarId() != null) {
                    preparedStatement.setLong(5, model.getAvatarId());
                } else {
                    preparedStatement.setNull(5, Types.NULL);
                }
                preparedStatement.setString(6, model.getRole().toString());


                int affect = preparedStatement.executeUpdate();

                if (affect != 1) {
                    throw new SQLException("Cannot insert account");
                }

                try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                    if (generatedIds.next()) {
                        model.setId(generatedIds.getLong("id"));
                    } else {
                        throw new SQLException("Cannot retrieve id");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
                preparedStatement.setString(1, model.getSurname());
                preparedStatement.setString(2, model.getUsername());
                preparedStatement.setString(3, model.getEmail());
                preparedStatement.setString(4, model.getPassword());
                preparedStatement.setLong(5, model.getAvatarId());

                preparedStatement.setString(6, model.getRole().toString());
                preparedStatement.setLong(7, model.getId());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Обновление пользователя не удалось, ни одна строка не была изменена.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        return model;

    }

    @Override
    public List<User> getAll() {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .surname(resultSet.getString("surname"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .avatarId(resultSet.getLong("avatar_id") == 0 ? null : resultSet.getLong("avatar_id"))
                        .build();
                result.add(user);
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
    public Optional<User> findById(Long id) {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID);

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .surname(resultSet.getString("surname"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .avatarId(resultSet.getLong("avatar_id") == 0 ? null : resultSet.getLong("avatar_id"))
                        .build();
                return Optional.of(user);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByName(String username) {


        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_USERNAME);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            User user = User.builder()
                    .id(resultSet.getLong("id"))
                    .username(resultSet.getString("username"))
                    .surname(resultSet.getString("surname"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .avatarId(resultSet.getLong("avatar_id") == 0 ? null : resultSet.getLong("avatar_id"))
                    .build();
            return Optional.of(user);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Optional<User> findByEmail(String email) {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_EMAIL);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            User user = User.builder()
                    .id(resultSet.getLong("id"))
                    .username(resultSet.getString("username"))
                    .surname(resultSet.getString("surname"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .avatarId(resultSet.getLong("avatar_id") == 0 ? null : resultSet.getLong("avatar_id"))
                    .build();
            return Optional.of(user);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void updateAvatarForUser(String email, Long fileId) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_AVATAR);
            preparedStatement.setLong(1, fileId);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
