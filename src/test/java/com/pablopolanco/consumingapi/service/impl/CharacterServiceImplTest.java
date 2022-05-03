package com.pablopolanco.consumingapi.service.impl;

import com.pablopolanco.consumingapi.client.CharacterAPIClient;
import com.pablopolanco.consumingapi.exception.CharacterServiceException;
import com.pablopolanco.consumingapi.model.Character;
import com.pablopolanco.consumingapi.model.Location;
import com.pablopolanco.consumingapi.model.Origin;
import com.pablopolanco.consumingapi.responseDTO.CharacterResponseDTO;
import com.pablopolanco.consumingapi.responseDTO.OriginResponseDTO;
import com.pablopolanco.consumingapi.service.CharacterService;
import com.pablopolanco.consumingapi.service.OriginService;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceImplTest {

    @Mock
    private CharacterAPIClient characterAPIClient;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private OriginService originServiceImpl;

    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        characterService = new CharacterServiceImpl(characterAPIClient,modelMapper,originServiceImpl);
    }

    @Test
    void should_return_CharacterResponseDTO() {
        CharacterResponseDTO responseExpected = buildCharacterResponseDTO();
        int characterId = 1;
        int locationId = 1;

        //GIVEN
        when(characterAPIClient.character(characterId)).thenReturn(buildCharacter());
        when(modelMapper.map(buildCharacter(), CharacterResponseDTO.class)).thenReturn(mapCharacterToCharacterResponseDTO());
        when(originServiceImpl.getOriginResponseDTO(locationId)).thenReturn(buildOriginResponseDTO());

        //WHEN
        CharacterResponseDTO characterResponseDTO = characterService.getCharacterResponseDTO(characterId);

        //THEN
        assertEquals(responseExpected, characterResponseDTO);

    }
    @Test
    void should_throw_exception_when_id_is_not_found() {
        String messageExpected = "Not found";
        int characterId = 0;

        //GIVEN
        when(characterAPIClient.character(characterId)).thenThrow(new CharacterServiceException("Not found"));

        //WHEN
        Throwable throwable = assertThrows(CharacterServiceException.class, () -> characterService.getCharacterResponseDTO(characterId));

        //THEN
        assertEquals(messageExpected, throwable.getMessage());

    }

    private CharacterResponseDTO buildCharacterResponseDTO (){

        List<String> residents = Stream.of("https://rickandmortyapi.com/api/character/8","https://rickandmortyapi.com/api/character/14").collect(Collectors.toList());
        OriginResponseDTO originResponseDTO = new OriginResponseDTO("Earth", "https://rickandmortyapi.com/api/location/1", "Dimension C-137", residents);
        return new CharacterResponseDTO(1, "Rick Sanchez", "Alive", "Human", "", 2, originResponseDTO);
    }

    private Character buildCharacter(){
        return new Character(1,
                "Rick Sanchez",
                "Alive",
                "Human",
                "",
                "Male",
                buildOrigin(),
                buildLocation(),
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                Stream.of("https://rickandmortyapi.com/api/episode/1", "https://rickandmortyapi.com/api/episode/2").collect(Collectors.toList()),
                "https://rickandmortyapi.com/api/character/1",
                "2017-11-04T18:48:46.250Z");
    }

    private Location buildLocation(){
        return new Location(1,
                "Earth",
                "Planet",
                "Dimension C-137",
                Stream.of("https://rickandmortyapi.com/api/character/8","https://rickandmortyapi.com/api/character/14").collect(Collectors.toList()),
                "https://rickandmortyapi.com/api/location/1",
                "2017-11-10T12:42:04.162Z");
    }

    private Origin buildOrigin(){
        return new Origin("Earth","https://rickandmortyapi.com/api/location/1");
    }
    private CharacterResponseDTO mapCharacterToCharacterResponseDTO(){

        Character character = buildCharacter();
        CharacterResponseDTO characterResponseDTO = new CharacterResponseDTO();
        characterResponseDTO.setId(character.getId());
        characterResponseDTO.setName(character.getName());
        characterResponseDTO.setStatus(character.getStatus());
        characterResponseDTO.setSpecies(character.getSpecies());
        characterResponseDTO.setType(character.getType());

        return characterResponseDTO;
    }

    private OriginResponseDTO buildOriginResponseDTO (){
        return new OriginResponseDTO("Earth",
                "https://rickandmortyapi.com/api/location/1",
                "Dimension C-137",
                Stream.of("https://rickandmortyapi.com/api/character/8","https://rickandmortyapi.com/api/character/14").collect(Collectors.toList()));
    }
}