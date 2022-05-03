package com.pablopolanco.consumingapi.controller;


import com.pablopolanco.consumingapi.exception.CharacterServiceException;
import com.pablopolanco.consumingapi.responseDTO.CharacterResponseDTO;
import com.pablopolanco.consumingapi.responseDTO.OriginResponseDTO;
import com.pablopolanco.consumingapi.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        int statusExpected = 200;

        //WHEN
        ResponseEntity responseEntity  = characterController.getCharacter(2);

        //THEN
        assertEquals(statusExpected, responseEntity.getStatusCodeValue());
    }

    @Test
    void should_throw_exception_when_service_fail() {
        String messageExpected = "Some error";
        int characterId = 0;

        //GIVEN
        when(characterService.getCharacterResponseDTO(characterId)).thenThrow(new CharacterServiceException("Some error"));

        //WHEN
        ResponseStatusException throwable = assertThrows(ResponseStatusException.class, () -> characterController.getCharacter(characterId));

        //THEN
        assertEquals(messageExpected, throwable.getReason());

    }

    @Test
    void should_return_CharacterResponseDTO (){
        CharacterResponseDTO expectedResponse = buildCharacterResponseDTO();
        int characterId = 2;

        //GIVEN
        when(characterService.getCharacterResponseDTO(characterId)).thenReturn(expectedResponse);

        //WHEN
        ResponseEntity<CharacterResponseDTO> responseEntity  = characterController.getCharacter(characterId);

        //THEN
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    private CharacterResponseDTO buildCharacterResponseDTO (){

        List<String> residents = Stream.of("https://rickandmortyapi.com/api/character/8","https://rickandmortyapi.com/api/character/14").collect(Collectors.toList());
        OriginResponseDTO originResponseDTO = new OriginResponseDTO("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3", "unknown", residents);
        return new CharacterResponseDTO(2, "Morty Smith", "Alive", "Human", "", 10, originResponseDTO);
    }
}
