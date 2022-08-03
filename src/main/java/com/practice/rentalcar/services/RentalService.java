package com.practice.rentalcar.services;

import com.practice.rentalcar.exceptions.RentalException;
import com.practice.rentalcar.models.Car;
import com.practice.rentalcar.models.Rental;
import com.practice.rentalcar.models.User;
import com.practice.rentalcar.repositories.CarRepository;
import com.practice.rentalcar.repositories.RentalRepository;
import com.practice.rentalcar.repositories.UserRepository;

import java.util.Date;
import java.util.Optional;

public class RentalService {
    private final RentalRepository rentalRepository;
    private UserRepository userRepository;
    private CarRepository carRepository;


    public RentalService(UserRepository userRepository, CarRepository carRepository, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.rentalRepository = rentalRepository;
    }

    public Rental rentCar(Long userId, Long carId) throws RentalException {
        //check if user exists, car exists & is car rented?//
        Optional<User> user = userRepository.findUserById(userId);
        if (user.isEmpty()) {
            throw new RentalException("User not found");
        }

        Optional<Car> car = carRepository.findCarById(carId);
        if (car.isEmpty()) {
            throw new RentalException("Car not found");
        }

        Optional<Rental> rental = rentalRepository.findCarById(car.get().getId());
        if (rental.isPresent()) {
            throw new RentalException("Car already rented");
        }

        Rental rent= new Rental();
        rent.setCar(car.get());
        rent.setUser(user.get());
        rent.setRentalDate(new Date());

        Rental createdRental= rentalRepository.saveRental(rent);

        return createdRental;

    }

}
