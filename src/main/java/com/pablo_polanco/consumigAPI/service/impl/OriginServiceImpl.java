package com.pablo_polanco.consumigAPI.service.impl;

import com.pablo_polanco.consumigAPI.client.APIClient;
import com.pablo_polanco.consumigAPI.model.Location;
import com.pablo_polanco.consumigAPI.responseDTO.OriginResponseDTO;
import com.pablo_polanco.consumigAPI.service.OriginService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginServiceImpl implements OriginService {

    @Autowired
    private APIClient apiClient;
    @Autowired
    private ModelMapper modelMapper;

    
    /** 
     * @param id
     * @return OriginRsponseDTO
     *Obiene el planeta(Location) de la API de Rick and Morty segun el id dado y lo mapea a OriginResponseDTO
     */
    @Override
    public OriginResponseDTO getOriginResponseDTO(Integer id) {

        Location location = apiClient.location(id);
        OriginResponseDTO originRsponseDTO = modelMapper.map(location, OriginResponseDTO.class);
        
        return originRsponseDTO;
    }
    
}
