package kpfu.itis.dto;

import kpfu.itis.models.UserRole;
import lombok.*;


@Data
@Builder
public class SignUpForm {
    private String surname;
    private String username;
    private String password;
    private String email;
    private UserRole role;


}
