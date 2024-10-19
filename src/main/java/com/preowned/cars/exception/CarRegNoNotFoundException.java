package com.preowned.cars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CarRegNoNotFoundException extends RuntimeException{
    public CarRegNoNotFoundException(String message){
        super(message);
    }
}
