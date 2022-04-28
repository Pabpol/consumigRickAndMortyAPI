package com.pablopolanco.consumingapi.service.impl;

import com.pablopolanco.consumingapi.client.LocationAPIClient;
import com.pablopolanco.consumingapi.responseDTO.OriginResponseDTO;
import com.pablopolanco.consumingapi.service.OriginService;
import com.pablopolanco.consumingapi.model.Location;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OriginServiceImpl implements OriginService {

    private final LocationAPIClient locationAPIClient;
    private final ModelMapper modelMapper;

    public OriginServiceImpl(LocationAPIClient locationAPIClient, ModelMapper modelMapper) {
        this.locationAPIClient = locationAPIClient;
        this.modelMapper = modelMapper;
    }


    /** 
     * @param id
     * @return OriginRsponseDTO
     *Obiene el planeta(Location) de la API de Rick and Morty segun el id dado y lo mapea a OriginResponseDTO
     */
    @Override
    public OriginResponseDTO getOriginResponseDTO(Integer id) {

        Location location = locationAPIClient.location(id);

        return modelMapper.map(location, OriginResponseDTO.class);
    }
    
}
