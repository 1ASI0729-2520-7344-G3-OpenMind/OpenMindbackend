package com.openmind.IAM.Domain.Model.ValueObjects;

public record UserId(Long value){
    public UserId{
        if(value == null || value <= 0){
            throw new IllegalArgumentException("UserId cannot be null or negative");
        }
    }
}
