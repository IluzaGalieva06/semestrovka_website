package kpfu.itis.services.impl;

import kpfu.itis.dto.SignUpForm;
import kpfu.itis.dto.UserDto;
import kpfu.itis.models.User;
import kpfu.itis.models.UserRole;
import kpfu.itis.services.UserMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .surname(user.getSurname())
                .email(user.getEmail())
                .avatarId(user.getAvatarId())
                .role(user.getRole())
                .build();
    }

    @Override
    public User toUser(UserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .build();
    }

    @Override
    public User toUser(SignUpForm dto) {
        return User.builder()
                .username(dto.getUsername())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }
    @Override
    public Object from(ResultSet rs, long rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .surname(rs.getString("surname"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .role(UserRole.valueOf(rs.getString("role")))
                .build();
    }
}
