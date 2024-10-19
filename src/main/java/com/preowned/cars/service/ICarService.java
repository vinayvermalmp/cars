package com.preowned.cars.service;

import com.preowned.cars.dto.CarDto;
import com.preowned.cars.entity.Car;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface ICarService {
    //post
    CarDto addCar(CarDto car);

    // get
    public List<CarDto> getAllCars();
    CarDto getCar(String regNo);
    public List<CarDto> getAllCarsByBrandName(String brandName);
    public List<CarDto> getAllCarsByCarType(String carType);

    // delete
    boolean deleteCar(String regNo);
    boolean deleteAllCars();

    //Put
    public boolean updateCar(String regNo, CarDto carDto);
}
