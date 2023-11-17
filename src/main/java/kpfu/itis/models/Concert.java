package kpfu.itis.models;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Concert {
    private Long id;
    private String name;
    private Date date;
    private String description;
    private Long posterId;
    private Long locationId;
    private Long price;


}
