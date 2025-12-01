package com.openmind.IAM.Domain.Services;

import com.openmind.IAM.Domain.Model.ValueObjects.HashedPassword;

public interface PasswordEncoder {
    HashedPassword encode(String rawPassword);
    boolean matches(String rawPassword, HashedPassword hashedPassword);
}
