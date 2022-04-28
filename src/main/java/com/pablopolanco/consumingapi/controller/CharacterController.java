package com.pablopolanco.consumingapi.controller;

import com.pablopolanco.consumingapi.exception.CharacterServiceException;
import com.pablopolanco.consumingapi.responseDTO.CharacterResponseDTO;
import com.pablopolanco.consumingapi.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/character")
public class CharacterController {

    private final CharacterService characterServiceImpl;

    public CharacterController(CharacterService characterServiceImpl) {
        this.characterServiceImpl = characterServiceImpl;
    }


    /**
     * @param id
     * @return CharacterResponseDTO
     *         Endpoint que entrega un CharacterResponseDTO según id
     * @throws Exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDTO> getCharacter(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(characterServiceImpl.getCharacterResponseDTO(id),HttpStatus.OK);
        } catch (CharacterServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        

    }

}
