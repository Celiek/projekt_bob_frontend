package com.test.bob.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OfferNotFoundException extends RuntimeException{

    public OfferNotFoundException(Long id){
        super("Oferta o podanym id nie istniej " + id);
    }
}
