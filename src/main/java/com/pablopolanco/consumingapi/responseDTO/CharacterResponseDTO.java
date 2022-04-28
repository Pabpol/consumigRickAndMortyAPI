package com.pablopolanco.consumingapi.responseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterResponseDTO {

    @JsonProperty(index = 0)
    private Integer id;

    @JsonProperty(index = 1)
    private String name;

    @JsonProperty(index = 2)
    private String status;

    @JsonProperty(index = 3)
    private String species;

    @JsonProperty(index = 4)
    private String type;

    @JsonProperty(value = "episode_count", index = 5)
    private Integer episodeCount;

    @JsonProperty(index = 6)
    private OriginResponseDTO origin;
}
