package com.pablo_polanco.consumigAPI.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CharacterServiceException extends RuntimeException {
    
    public CharacterServiceException ( String message){
        super(message);
    }
    
}
