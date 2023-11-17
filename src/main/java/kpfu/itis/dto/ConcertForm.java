package kpfu.itis.dto;

import lombok.*;


import java.sql.Date;

@Data
@AllArgsConstructor
@Builder
@Getter
public class ConcertForm {
    private Long id;
    private String name;
    private String location;
    private Date date;
    private String description;
    private Long posterId;
    private Long price;

}
