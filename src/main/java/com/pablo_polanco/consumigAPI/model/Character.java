package com.pablo_polanco.consumigAPI.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Character {
    
    private String image;
    private String gender;
    private String species;
    private String created;
    private Origin origin;
    private String name;
    private Location location;
    private List<String> episode;
    private Integer id;
    private String type;
    private String url;
    private String status;
}
