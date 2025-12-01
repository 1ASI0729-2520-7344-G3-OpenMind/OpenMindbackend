package com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects;

import java.util.Objects;

public record customerId(Long value) {
    public customerId {
        Objects.requireNonNull(value, "CustomerId cannot be null");
        if(value <= 0){
            throw new IllegalArgumentException("CustomerId cannot be negative");
        }
    }

    @Override
    public String toString(){
        return Long.toString(value);
    }
}
