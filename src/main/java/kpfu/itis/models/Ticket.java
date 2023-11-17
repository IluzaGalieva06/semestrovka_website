package kpfu.itis.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Ticket {
    private Long id;
    private Long concertId;
    private Long userId;
    private Long price;
}
