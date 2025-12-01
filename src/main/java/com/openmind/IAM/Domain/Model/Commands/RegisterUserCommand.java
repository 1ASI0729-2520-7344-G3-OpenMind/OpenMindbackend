package com.openmind.IAM.Domain.Model.Commands;

public record RegisterUserCommand(String name, String email, String password) {
}
