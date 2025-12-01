package com.openmind.IAM.Domain.Model.Exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(){
        super("The Email is already registered");
    }
}
