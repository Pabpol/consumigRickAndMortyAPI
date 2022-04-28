package com.pablopolanco.consumingapi.client;

import com.pablopolanco.consumingapi.model.Character;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "characterRickAndMortyAPI", url = "https://rickandmortyapi.com/api/character")
public interface CharacterAPIClient {

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Character character (@PathVariable Integer id);

}
