package com.practice.rentalcar.repositories;
import com.practice.rentalcar.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserById(Long id);
}
