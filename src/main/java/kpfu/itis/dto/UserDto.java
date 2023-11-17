package kpfu.itis.dto;

import kpfu.itis.models.UserRole;
import lombok.*;


@Data
@Builder
public class UserDto {
    private Long id;
    private String surname;
    private String username;
    private String email;
    private Long avatarId;
    private UserRole role;

}
