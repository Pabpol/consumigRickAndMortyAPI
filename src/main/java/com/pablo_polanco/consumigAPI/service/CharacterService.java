package com.pablo_polanco.consumigAPI.service;

import com.pablo_polanco.consumigAPI.responseDTO.CharacterResponseDTO;


public interface CharacterService {

    public CharacterResponseDTO getCharacterResponseDTO (Integer id) throws Exception;
    
}
