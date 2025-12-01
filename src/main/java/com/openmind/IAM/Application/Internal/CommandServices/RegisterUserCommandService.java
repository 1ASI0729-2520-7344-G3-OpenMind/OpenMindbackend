package com.openmind.IAM.Application.Internal.CommandServices;

import com.openmind.IAM.Domain.Model.Aggregates.User;
import com.openmind.IAM.Domain.Model.Commands.RegisterUserCommand;
import com.openmind.IAM.Domain.Model.Exceptions.UserAlreadyExistsException;
import com.openmind.IAM.Domain.Model.ValueObjects.Email;
import com.openmind.IAM.Domain.Repositories.UserRepository;
import com.openmind.IAM.Domain.Services.PasswordEncoder;
import com.openmind.IAM.Infraestructure.Config.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUserCommandService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public String handle(RegisterUserCommand command){
        Email email = new Email(command.email());
        if(userRepository.existsByEmail(email)){
            throw new UserAlreadyExistsException();
        }
        User user = User.builder().name(command.name()).email(command.email()).password(command.password(), passwordEncoder).build();

        userRepository.save(user);

        User savedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error al guardar usuario"));

        return jwtService.generateToken(savedUser.getId().value(),
                savedUser.getEmail().value());
    }
}
