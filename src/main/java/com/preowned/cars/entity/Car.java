package com.preowned.cars.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "reg_no")
    private String regNo;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "yr")
    private int year;

    private int kms;   // table column is "kms" so no need for @Column here
    private int price;
}
