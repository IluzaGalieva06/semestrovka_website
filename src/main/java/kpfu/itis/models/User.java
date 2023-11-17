package kpfu.itis.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String surname;
    private String username;
    private String password;
    private String email;
    private Long avatarId;
    private UserRole role;
    private List<Ticket> ticketList;

}
