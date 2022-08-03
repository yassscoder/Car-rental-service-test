package com.practice.rentalcar.repositories;

import com.practice.rentalcar.models.Car;

import java.util.Optional;

public interface CarRepository {
    Optional<Car> findCarById(Long id);
}
