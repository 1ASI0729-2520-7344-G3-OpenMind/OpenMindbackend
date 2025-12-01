package com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects;

import java.util.Objects;
import java.util.UUID;

public record conversationId(UUID value) {
    public conversationId {
        Objects.requireNonNull(value, "ConversationId cannot be null");
    }

    public static conversationId newId(){
        return new conversationId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
