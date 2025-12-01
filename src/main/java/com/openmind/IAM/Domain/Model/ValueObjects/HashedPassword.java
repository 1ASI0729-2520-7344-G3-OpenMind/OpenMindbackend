package com.openmind.IAM.Domain.Model.ValueObjects;

public record HashedPassword(String value) {
    public HashedPassword{
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException();
        }
    }
}
