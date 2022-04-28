package com.pablopolanco.consumingapi.service;

import com.pablopolanco.consumingapi.exception.CharacterServiceException;
import com.pablopolanco.consumingapi.responseDTO.CharacterResponseDTO;


public interface CharacterService {

    public CharacterResponseDTO getCharacterResponseDTO (Integer id) throws CharacterServiceException;
    
}
