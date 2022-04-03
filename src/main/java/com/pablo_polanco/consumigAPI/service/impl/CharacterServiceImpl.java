package com.pablo_polanco.consumigAPI.service.impl;

import com.pablo_polanco.consumigAPI.client.APIClient;
import com.pablo_polanco.consumigAPI.exception.CharacterServiceException;
import com.pablo_polanco.consumigAPI.model.Character;
import com.pablo_polanco.consumigAPI.responseDTO.CharacterResponseDTO;
import com.pablo_polanco.consumigAPI.responseDTO.OriginResponseDTO;
import com.pablo_polanco.consumigAPI.service.CharacterService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feign.FeignException;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private APIClient apiClient;
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

            Character character = apiClient.character(id);
            OriginResponseDTO originRsponseDTO;
            CharacterResponseDTO characterResponseDTO = modelMapper.map(character, CharacterResponseDTO.class);

            // Si la url de origen es distinta a vacia, Obtiene el planeta(Location) y lo mapea a OriginResponseDTO
            if (!character.getOrigin().getUrl().equals("")) {
                Integer idOrigin = getOriginId(character);
                originRsponseDTO = originServiceImpl.getOriginResponseDTO(idOrigin);
                characterResponseDTO.setOrigin(originRsponseDTO);
            }

            // si la url está vacía, define el origen como null
            else {
                characterResponseDTO.setOrigin(null);

            }

            characterResponseDTO.setEpisodeCount(character.getEpisode().size());

            // Retorna el objeto construido
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
        Integer originId = Integer
                .parseInt(character.getOrigin().getUrl().split("https://rickandmortyapi.com/api/location/")[1]);
        return originId;
    }

}
