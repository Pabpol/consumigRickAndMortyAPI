package com.pablo_polanco.consumigAPI.responseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginResponseDTO {
    
    private String name;
    private String url;
    private String dimension;
    private List<String> residents;
}
