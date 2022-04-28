package com.pablopolanco.consumingapi.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablopolanco.consumingapi.responseDTO.CharacterResponseDTO;
import com.pablopolanco.consumingapi.responseDTO.OriginResponseDTO;
import com.pablopolanco.consumingapi.exception.CharacterServiceException;
import com.pablopolanco.consumingapi.service.impl.CharacterServiceImpl;
import com.pablopolanco.consumingapi.service.impl.OriginServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterServiceImpl characterServiceImpl;

    @MockBean
    private OriginServiceImpl originServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;


    //TODO test unitario controller

    @Test
    void getCharacterSuccesed () throws Exception{
        
        List<String> residents = Stream.of("https://rickandmortyapi.com/api/character/8","https://rickandmortyapi.com/api/character/14").collect(Collectors.toList());
        OriginResponseDTO originResponseDTO = new OriginResponseDTO("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3", "unknown", residents);
        CharacterResponseDTO characterResponseDTO = new CharacterResponseDTO(2, "Morty Smith", "Alive", "Human", "", 10, originResponseDTO);

        when(characterServiceImpl.getCharacterResponseDTO(2)).thenReturn(characterResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/character/2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Morty Smith"))
                .andExpect(jsonPath("$.status").value("Alive"))
                .andExpect(jsonPath("$.species").value("Human"))
                .andExpect(jsonPath("$.type").value(""))
                .andExpect(jsonPath("$.episodeCount").value(10))
                .andExpect(jsonPath("$.origin.name").value("Citadel of Ricks"))
                .andExpect(jsonPath("$.origin.url").value("https://rickandmortyapi.com/api/location/3"))
                .andExpect(jsonPath("$.origin.dimension").value("unknown"))
                .andExpect(jsonPath("$.origin.residents[0]").value("https://rickandmortyapi.com/api/character/8"))
                .andExpect(jsonPath("$.origin.residents[1]").value("https://rickandmortyapi.com/api/character/14"))
                .andExpect(content().json(objectMapper.writeValueAsString(characterResponseDTO)));

        
        verify(characterServiceImpl).getCharacterResponseDTO(2);
    }
    
    @Test
    void getCharacterNotFound () throws Exception{
        when(characterServiceImpl.getCharacterResponseDTO(1000)).thenThrow(new CharacterServiceException("Not Found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/character/1000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
    }
}
