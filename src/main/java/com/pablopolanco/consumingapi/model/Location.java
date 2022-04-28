package com.pablopolanco.consumingapi.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private String created;
    private String name;
    private List<String> residents;
    private Integer id;
    private String type;
    private String dimension;
    private String url;
}
