package kpfu.itis.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class SignInForm {
    private Long id;
    private String email;
    private String password;


}
