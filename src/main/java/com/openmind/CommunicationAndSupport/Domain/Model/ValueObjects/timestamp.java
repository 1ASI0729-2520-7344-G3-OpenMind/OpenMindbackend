package com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects;

import java.time.Instant;
import java.util.Objects;

public record timestamp(Instant value) {
    public timestamp{
        Objects.requireNonNull(value, "Timestamp cannot be null");
    }

    public static timestamp now(){
        return new timestamp(Instant.now());
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
