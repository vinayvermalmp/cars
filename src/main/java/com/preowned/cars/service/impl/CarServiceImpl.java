package com.preowned.cars.service.impl;

import com.preowned.cars.dto.CarDto;
import com.preowned.cars.entity.Car;
import com.preowned.cars.exception.CarRegMismatchException;
import com.preowned.cars.exception.CarRegNoAlreadyExistsException;
import com.preowned.cars.exception.CarRegNoNotFoundException;
import com.preowned.cars.mapper.CarMapper;
import com.preowned.cars.repository.CarRepository;
import com.preowned.cars.service.ICarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarServiceImpl implements ICarService {

    private CarRepository carRepository;

    private CarMapper mapper;
    @Override
    public CarDto addCar(CarDto carDto) {
        if (carRepository.findByRegNo(carDto.getRegNo()).isPresent()) {
            throw new CarRegNoAlreadyExistsException("CAR WITH REG NO "+carDto.getRegNo()+" already exist ");
        }
        Car car = mapper.mapToCar(carDto);
        Car savedCar = carRepository.save(car);
        return mapper.mapToCarDto(savedCar);
    }

    @Override
    public List<CarDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(car -> mapper.mapToCarDto(car)).collect(Collectors.toList());
    }

    @Override
    public CarDto getCar(String regNo) {
        Car car = carRepository.findByRegNo(regNo).orElseThrow(() -> new CarRegNoNotFoundException("Car reg number not found in db! : "+regNo));
        return mapper.mapToCarDto(car);
    }

    @Override
    public List<CarDto> getAllCarsByBrandName(String brandName) {
        List<Car> cars = carRepository.findAllByBrandName(brandName);
        return cars.stream().map(car -> mapper.mapToCarDto(car)).collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getAllCarsByCarType(String carType) {
        List<Car> cars = carRepository.findAllByCarType(carType);
        return cars.stream().map(car -> mapper.mapToCarDto(car)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCar(String regNo) {
        Car car = carRepository.findByRegNo(regNo).orElseThrow(() -> new CarRegNoNotFoundException("Car reg number not found in db! : "+regNo));
        carRepository.deleteByRegNo(regNo); // derived query
        return true;
    }

    @Override
    public boolean deleteAllCars() {
        carRepository.deleteAll(); // already available
        return true;
    }

    @Override
    public boolean updateCar(String carRegNoFromURI, CarDto carDto) {
        if(!carRegNoFromURI.equals(carDto.getRegNo())) { // reg numbers do not match, generate an exception
            throw new CarRegMismatchException("Car reg numbers mismatch!. URI: "+carRegNoFromURI+", Entity Body: "+carDto.getRegNo());
        }
        //Car car = carRepository.findByRegNo(carRegNoFromURI).orElseThrow(()-> new CarRegMismatchException("Car reg numbers mismatch!. URI: " + carRegNoFromURI + ", Entity Body: " + carDto.getRegNo()));
        // this is an update as the regNo is already in the database
        // Update - save() does insert as we know; it will do update if the primary key is present.
        // However, as I am letting the db generate ID's for the primary keys, this will not work.
        // Thus, we need to annotate the update method with @Transaction, @Query and @Modifying.
        // https://stackoverflow.com/questions/11881479/how-do-i-update-an-entity-using-spring-data-jpa

        carRepository.updateCar(carDto.getBrandName(), carDto.getModelName(), carDto.getCarType(),
                carDto.getYear(), carDto.getKms(), carDto.getPrice(), carDto.getRegNo());
        return true;
    }
}
