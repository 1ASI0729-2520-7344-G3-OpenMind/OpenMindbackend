package com.openmind.IAM.Infraestructure.Config;

import com.openmind.IAM.Domain.Model.ValueObjects.HashedPassword;
import com.openmind.IAM.Domain.Services.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoder {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public HashedPassword encode(String rawPassword) {
        return new HashedPassword(encoder.encode(rawPassword));
    }

    @Override
    public boolean matches(String rawPassword, HashedPassword hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword.value());
    }
}
