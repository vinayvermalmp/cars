package com.preowned.cars.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CarDto{
    @NotEmpty(message = "Brand name should not be empty")
    private String brandName;
    private String modelName;
    @Size(min = 5, max = 10, message = "Registration number must be between 5-10 characters")
    private String regNo;

    @NotEmpty
    private String carType;

    @Min(value = 4)
    private int year;

    private  int kms;

    private  int price;
}

