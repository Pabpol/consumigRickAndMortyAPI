package com.pablo_polanco.consumigAPI.responseDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterResponseDTO {
    
    private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    private Integer episodeCount;
    private OriginResponseDTO origin;
}
