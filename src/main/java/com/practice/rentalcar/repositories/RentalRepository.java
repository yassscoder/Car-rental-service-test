package com.practice.rentalcar.repositories;

import com.practice.rentalcar.models.Car;
import com.practice.rentalcar.models.Rental;
import com.practice.rentalcar.models.User;

import java.util.Optional;

public interface RentalRepository {
    Optional<Rental> findRentalById(Long id);
    Optional<Rental> findCarById(Long id);

   Rental  saveRental(Rental rental);
}
