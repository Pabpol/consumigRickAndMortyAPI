package com.pablopolanco.consumingapi.controller;


import com.pablopolanco.consumingapi.responseDTO.CharacterResponseDTO;
import com.pablopolanco.consumingapi.responseDTO.OriginResponseDTO;
import com.pablopolanco.consumingapi.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CharacterControllerTest {

    private CharacterController characterController;
    @Mock
    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        characterController = new CharacterController(characterService);
    }

    @Test
    void should_return_200_status() {
        int statusExpexted = 200;

        //given

        List<String> residents = Stream.of("https://rickandmortyapi.com/api/character/8","https://rickandmortyapi.com/api/character/14").collect(Collectors.toList());
        OriginResponseDTO originResponseDTO = new OriginResponseDTO("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3", "unknown", residents);
        CharacterResponseDTO characterResponseDTOGiven = new CharacterResponseDTO(2, "Morty Smith", "Alive", "Human", "", 10, originResponseDTO);
        when(characterService.getCharacterResponseDTO(2)).thenReturn(characterResponseDTOGiven);

        //WHEN
        ResponseEntity responseEntity  = characterController.getCharacter(2);

        //THEN
        assertEquals(statusExpexted, responseEntity.getStatusCodeValue());
    }
}
