package com.pablopolanco.consumingapi.client;

import com.pablopolanco.consumingapi.model.Location;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "locationRickAndMortyAPI", url = "https://rickandmortyapi.com/api/location")
public interface LocationAPIClient {

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Location location(@PathVariable Integer id);
}
