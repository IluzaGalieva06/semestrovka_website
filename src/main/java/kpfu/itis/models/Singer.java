package kpfu.itis.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Singer {
    private Long id;
    private String nickname;
    private String description;


}
