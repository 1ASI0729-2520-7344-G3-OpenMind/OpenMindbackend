package com.openmind.IAM.Interfaces.REST;

import com.openmind.IAM.Application.Internal.CommandServices.LoginCommandService;
import com.openmind.IAM.Application.Internal.CommandServices.RegisterUserCommandService;
import com.openmind.IAM.Domain.Model.Commands.LoginCommand;
import com.openmind.IAM.Domain.Model.Commands.RegisterUserCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"},
        allowedHeaders = "*",
        allowCredentials = "true")
@RequiredArgsConstructor
class AuthController {
    private final RegisterUserCommandService registerCommand;
    private final LoginCommandService loginCommand;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserCommand command){
        String token = registerCommand.handle(command);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginCommand command){
        String token = loginCommand.handle(command);
        return ResponseEntity.ok(Map.of("token", token));
    }
}

