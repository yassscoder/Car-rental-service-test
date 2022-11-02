package com.practice.rentalcar.RentService;

import com.practice.rentalcar.exceptions.RentalException;
import com.practice.rentalcar.models.Car;
import com.practice.rentalcar.models.Rental;
import com.practice.rentalcar.models.User;
import com.practice.rentalcar.repositories.CarRepository;
import com.practice.rentalcar.repositories.RentalRepository;
import com.practice.rentalcar.repositories.UserRepository;
import com.practice.rentalcar.services.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RentServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldFindUserById() {

        // Arrange
        User user = new User(1L, "Yas");

        //Act
        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

        //Assert
        assertEquals(user, userRepository.findUserById(1L).get());
        assertEquals(user.getName(), userRepository.findUserById(1L).get().getName());
        assertEquals(user.getName(), userRepository.findUserById(user.getId()).get().getName());
    }

    @Test
    void shouldFindCarById() {
        Car car = new Car(1L, "4356ORJ", "Mazda");
        Mockito.when(carRepository.findCarById(car.getId())).thenReturn(Optional.of(car));
        assertEquals(car, carRepository.findCarById(1L).get());
        assertEquals(car.getNumberPlate(), carRepository.findCarById(1L).get().getNumberPlate());
        assertEquals(car.getNumberPlate(), carRepository.findCarById(car.getId()).get().getNumberPlate());
    }

    @Test
    void shouldThrowExceptionIfUserDoesNotExist() {
        User user = new User(1L, "Yas");
        Car car = new Car(1L, "4356ORJ", "Mazda");
        RentalService rentService = new RentalService(userRepository, carRepository, rentalRepository);
        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.empty());
        Mockito.when(carRepository.findCarById(car.getId())).thenReturn(Optional.of(car));

        RentalException thrown = assertThrows(RentalException.class, () -> rentService.rentCar(user.getId(), car.getId()));

        assertEquals("User not found", thrown.getMessage());

    }

    @Test
    void shouldThrowExceptionIfCarDoesNotExist() {
        User user = new User(1L, "Yas");
        Car car = new Car(1L, "4356ORJ", "Mazda");
        RentalService rentService = new RentalService(userRepository, carRepository, rentalRepository);
        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findCarById(car.getId())).thenReturn(Optional.empty());

        RentalException thrown = assertThrows(RentalException.class, () -> rentService.rentCar(user.getId(), car.getId()));

        assertEquals("Car not found", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionIfCarIsAlreadyRented() {
        User user = new User(1L, "Yas");
        Car car = new Car(1L, "4356ORJ", "Mazda");
        Date rentalDate = new Date();
        Rental rent = new Rental(1L, car, user, rentalDate);

        RentalService rentService = new RentalService(userRepository, carRepository, rentalRepository);

        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findCarById(car.getId())).thenReturn(Optional.of(car));
        Mockito.when(rentalRepository.findCarById(car.getId())).thenReturn(Optional.of(rent));

        RentalException thrown = assertThrows(RentalException.class, () -> rentService.rentCar(user.getId(), car.getId()));

        assertEquals("Car already rented", thrown.getMessage());
    }


    @Test
    void shouldRentACar() {
        Car car = new Car(1L, "4356ORJ", "Mazda");
        User user = new User(1L, "Yas");
        Rental rental = new Rental(car, user);

        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findCarById(car.getId())).thenReturn(Optional.of(car));
        Mockito.when(rentalRepository.findCarById(car.getId())).thenReturn(Optional.empty());
        Mockito.when(rentalRepository.saveRental(rental)).thenReturn(rental);

        Rental currentRental = rentalRepository.saveRental(rental);

        assertEquals(user, userRepository.findUserById(user.getId()).get());
        assertEquals(car, carRepository.findCarById(car.getId()).get());
        assertEquals(rental, currentRental);
        assertEquals(rental.getId(), currentRental.getId());

        assertEquals(rental.getCar(), currentRental.getCar());
        assertEquals(rental.getUser(), currentRental.getUser());
        assertEquals(rental.getUser().getName(), currentRental.getUser().getName());
        assertEquals("Yas", currentRental.getUser().getName());
        assertEquals("Mazda", currentRental.getCar().getBrand());
    }

}
