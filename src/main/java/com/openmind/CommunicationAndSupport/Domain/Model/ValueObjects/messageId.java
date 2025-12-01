package com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects;

import java.util.Objects;
import java.util.UUID;

public record messageId(UUID value) {
    public messageId{
        Objects.requireNonNull(value, "MessageId cannot be null");
    }

    public static messageId newId(){
        return new messageId(UUID.randomUUID());
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
