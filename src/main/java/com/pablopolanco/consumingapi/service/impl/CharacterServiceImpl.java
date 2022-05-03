package com.pablopolanco.consumingapi.service.impl;

import com.pablopolanco.consumingapi.client.CharacterAPIClient;
import com.pablopolanco.consumingapi.exception.CharacterServiceException;
import com.pablopolanco.consumingapi.model.Character;
import com.pablopolanco.consumingapi.responseDTO.CharacterResponseDTO;
import com.pablopolanco.consumingapi.service.CharacterService;

import com.pablopolanco.consumingapi.service.OriginService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import feign.FeignException;

@Service
public class CharacterServiceImpl implements CharacterService {


    private final CharacterAPIClient characterAPIClient;
    private final ModelMapper modelMapper;
    private final OriginService originServiceImpl;

    public CharacterServiceImpl(CharacterAPIClient characterAPIClient,
                                ModelMapper modelMapper,
                                OriginService originServiceImpl) {

        this.characterAPIClient = characterAPIClient;
        this.modelMapper = modelMapper;
        this.originServiceImpl = originServiceImpl;
    }


    /**
     * @param id
     * @return CharacterResponseDTO
     *         Character obtenido con OpenFeign
     */
    @Override
    public CharacterResponseDTO getCharacterResponseDTO(Integer id) throws CharacterServiceException {

        try {

            Character character = characterAPIClient.character(id);
            CharacterResponseDTO characterResponseDTO = modelMapper.map(character, CharacterResponseDTO.class);
            setOriginToCharacterResponseDTO(character, characterResponseDTO);
            characterResponseDTO.setEpisodeCount(countEpisode(character));

            return characterResponseDTO;

        } catch (FeignException fe) {
            throw new CharacterServiceException("Not found");
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


    private void setOriginToCharacterResponseDTO(Character character, CharacterResponseDTO characterResponseDTO ){

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
