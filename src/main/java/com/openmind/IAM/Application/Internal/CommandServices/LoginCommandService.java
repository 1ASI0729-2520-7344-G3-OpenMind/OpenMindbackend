package com.openmind.IAM.Application.Internal.CommandServices;

import com.openmind.IAM.Domain.Model.Aggregates.User;
import com.openmind.IAM.Domain.Model.Commands.LoginCommand;
import com.openmind.IAM.Domain.Model.Exceptions.InvalidCredentialsException;
import com.openmind.IAM.Domain.Model.ValueObjects.Email;
import com.openmind.IAM.Domain.Repositories.UserRepository;
import com.openmind.IAM.Domain.Services.PasswordEncoder;
import com.openmind.IAM.Infraestructure.Config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginCommandService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String handle(LoginCommand command){
        Email email = new Email(command.email());

        User user = userRepository.findByEmail(email).orElseThrow(InvalidCredentialsException::new);
        if(!passwordEncoder.matches(command.password(), user.getPassword())){
            throw new InvalidCredentialsException();
        }

        return jwtService.generateToken(user.getId().value(), user.getEmail().value());
    }
}
