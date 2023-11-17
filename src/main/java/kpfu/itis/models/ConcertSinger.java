package kpfu.itis.models;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConcertSinger {
    private Long concertId;
    private Long singerId;
}
