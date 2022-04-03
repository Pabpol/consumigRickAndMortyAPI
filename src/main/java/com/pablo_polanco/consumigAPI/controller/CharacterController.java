package com.pablo_polanco.consumigAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

import com.pablo_polanco.consumigAPI.exception.CharacterServiceException;
import com.pablo_polanco.consumigAPI.responseDTO.CharacterResponseDTO;
import com.pablo_polanco.consumigAPI.service.impl.CharacterServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterServiceImpl characterServiceImpl;

    /**
     * @param id
     * @return CharacterResponseDTO
     *         Endpoint que entrega un CharacterResponseDTO según id
     * @throws Exception
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterResponseDTO getCharacter(@PathVariable Integer id, HttpServletResponse response) {
        try {
            return characterServiceImpl.getCharacterResponseDTO(id);
        } catch (CharacterServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        

    }

}
