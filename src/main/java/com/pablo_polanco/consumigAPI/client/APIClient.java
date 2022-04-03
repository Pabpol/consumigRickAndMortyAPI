package com.pablo_polanco.consumigAPI.client;

import com.pablo_polanco.consumigAPI.model.Character;
import com.pablo_polanco.consumigAPI.model.Location;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RickAndMortyAPI", url = "https://rickandmortyapi.com/api")
public interface APIClient {
    
    //Consume la api de Rick and Morty con el endpoint https://rickandmortyapi.com/api/character/{id}
    @GetMapping(path = "/character/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Character character (@PathVariable Integer id);

    
    //Consume la api de Rick and Morty con el endpoint https://rickandmortyapi.com/api/location/{id}
    @GetMapping(path = "/location/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Location location(@PathVariable Integer id);
}
