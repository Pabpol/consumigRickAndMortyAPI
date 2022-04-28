package com.pablopolanco.consumingapi.service.impl;

import com.pablopolanco.consumingapi.client.CharacterAPIClient;
import com.pablopolanco.consumingapi.exception.CharacterServiceException;
import com.pablopolanco.consumingapi.model.Character;
import com.pablopolanco.consumingapi.responseDTO.CharacterResponseDTO;
import com.pablopolanco.consumingapi.service.CharacterService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feign.FeignException;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterAPIClient characterAPIClient;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OriginServiceImpl originServiceImpl;

    /**
     * @param id
     * @return CharacterResponseDTO
     *         Crea un CharacterResponseDTO según el id entregado a partir de un
     *         Character obtenido con OpenFeign
     */
    @Override
    public CharacterResponseDTO getCharacterResponseDTO(Integer id) throws CharacterServiceException {

        try {

            Character character = characterAPIClient.character(id);
            CharacterResponseDTO characterResponseDTO = modelMapper.map(character, CharacterResponseDTO.class);
            setOriginToCharacterResponeDTO(character, characterResponseDTO);
            characterResponseDTO.setEpisodeCount(countEpisode(character));

            return characterResponseDTO;

        } catch (FeignException fe) {
            throw new CharacterServiceException(fe.getMessage());
        }

    }

    /**
     * @param character
     * @return Integer
     *         Obtiene el id de Location a partir de la url de origen del Character
     */
    private Integer getOriginId(Character character) {
        return Integer.parseInt(character.getOrigin().getUrl().split("https://rickandmortyapi.com/api/location/")[1]);
    }


    private void setOriginToCharacterResponeDTO (Character character, CharacterResponseDTO characterResponseDTO ){

        // Si la url de origen es distinta a vacia, Obtiene el planeta(Location) y lo mapea a OriginResponseDTO
        if (!character.getOrigin().getUrl().equals("")) {
            Integer idOrigin = getOriginId(character);
            characterResponseDTO.setOrigin(originServiceImpl.getOriginResponseDTO(idOrigin));
        }
        // si la url está vacía, define el origen como null
        else {
            characterResponseDTO.setOrigin(null);

        }
    }

    private int countEpisode (Character character){
        return character.getEpisode().size();

    }

}
