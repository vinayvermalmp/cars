package com.preowned.cars.controller;

import com.preowned.cars.dto.CarDto;
import com.preowned.cars.entity.Car;
import com.preowned.cars.service.ICarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping( path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private ICarService iCarService; // injected due to @RestController (which maps to @Component), @AllArgsConstructor

    @PostMapping
    public ResponseEntity<CarDto> addCar(@Valid @RequestBody CarDto car, UriComponentsBuilder uriComponentsBuilder){
        System.out.println("XXX car is "+car);
        CarDto carDto = iCarService.addCar(car);

        URI locationUri = uriComponentsBuilder
                .path("/cars/"+carDto.getRegNo())
                .buildAndExpand(uriComponentsBuilder.toUriString())
                .toUri();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carDto);
    }

}
