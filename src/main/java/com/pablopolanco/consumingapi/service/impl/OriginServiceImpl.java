package com.pablopolanco.consumingapi.service.impl;

import com.pablopolanco.consumingapi.client.LocationAPIClient;
import com.pablopolanco.consumingapi.responseDTO.OriginResponseDTO;
import com.pablopolanco.consumingapi.service.OriginService;
import com.pablopolanco.consumingapi.model.Location;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginServiceImpl implements OriginService {

    @Autowired
    private LocationAPIClient locationAPIClient;
    @Autowired
    private ModelMapper modelMapper;

    
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
