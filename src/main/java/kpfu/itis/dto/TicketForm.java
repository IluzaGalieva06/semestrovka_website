package kpfu.itis.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TicketForm {
    private Long id;
    private String concertName;
    private String userEmail;
    private Long price;
}
