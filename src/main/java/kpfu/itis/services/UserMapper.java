package kpfu.itis.services;

import kpfu.itis.dto.SignUpForm;
import kpfu.itis.dto.UserDto;
import kpfu.itis.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserMapper {
    UserDto toDto(User user);
    User toUser(UserDto dto);
    User toUser(SignUpForm dto);
    Object from(ResultSet rs, long rowNum) throws SQLException;


}
