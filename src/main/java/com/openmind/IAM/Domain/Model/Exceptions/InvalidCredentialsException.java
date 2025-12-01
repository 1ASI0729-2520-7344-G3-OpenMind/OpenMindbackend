package com.openmind.IAM.Domain.Model.Exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("The Email or Password are invalid");
    }
}
