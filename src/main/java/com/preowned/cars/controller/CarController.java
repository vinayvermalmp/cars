package com.preowned.cars.controller;

import com.preowned.cars.dto.CarDto;
import com.preowned.cars.entity.Car;
import com.preowned.cars.service.ICarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping( path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private ICarService iCarService; // injected due to @RestController (which maps to @Component), @AllArgsConstructor

    //       /cars
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

    @GetMapping
    public List<CarDto> getAllCars(){
        return iCarService.getAllCars();
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAllCars(){
        iCarService.deleteAllCars();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping
    public ResponseEntity<String> putNotsuported(){
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build();
    }

    @GetMapping(value = "/car" , params = "brandName")
    public List<CarDto> getAllCarByBrandName(@RequestParam String brandName){  // must be ?brandName=XX in Postman
        System.out.println("brand Name" + brandName);
        return iCarService.getAllCarsByBrandName(brandName);
    }

    @GetMapping(value = "/car" , params = "carType")
    public List<CarDto> getAllCarByCarType(@RequestParam String carType){  // must be ?brandName=XX in Postman
        System.out.println("Car Type" + carType);
        return iCarService.getAllCarsByBrandName(carType);
    }

    //       /{carRegNo}
    @GetMapping(value = "/{carRegNo}")
    public ResponseEntity<CarDto> getCarDetail(@PathVariable String carRegNo){
        CarDto carDto = iCarService.getCar(carRegNo);
        return ResponseEntity.status(HttpStatus.OK).body(carDto);

    }

    @PutMapping(value = "/{carRegNo}")
    public ResponseEntity<String> updateCar(@PathVariable String carRegNo, @RequestBody CarDto car){
        iCarService.updateCar(carRegNo, car);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{carRegNo}")
    public ResponseEntity<String> deleteCarDetail(@PathVariable String carRegNo){
        iCarService.deleteCar(carRegNo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //    @OptionsMapping  - not available
//    @HeadMapping     - not available
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsCollectionOfCars() {  //      /cars
        return ResponseEntity
                .status(HttpStatus.OK) // 200 OK
                .allow(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE) // varargs list
                .build();

    }
    @RequestMapping(value = "/{carRegNo}", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsIndividualCar() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .build();

    }
    @RequestMapping(value = "/car", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsIndividualRequestParams() {
        return ResponseEntity
                .status(HttpStatus.OK) // success
                .allow(HttpMethod.HEAD, HttpMethod.GET)
                .build();

    }
}
