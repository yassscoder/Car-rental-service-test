package com.practice.rentalcar.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Car car;
    User user;
    Date rentalDate;

    public Rental(Car car, User user) {

        this.car = car;
        this.user = user;
    }
}
