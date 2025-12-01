package com.openmind.IAM.Domain.Repositories;

import com.openmind.IAM.Domain.Model.Aggregates.User;
import com.openmind.IAM.Domain.Model.ValueObjects.Email;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByEmail(Email email);

    boolean existsByEmail(Email email);
}
