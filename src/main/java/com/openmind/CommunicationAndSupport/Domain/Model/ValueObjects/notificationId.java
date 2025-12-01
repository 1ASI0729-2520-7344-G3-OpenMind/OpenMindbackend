package com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects;

import java.util.Objects;
import java.util.UUID;

public record notificationId(UUID value) {
    public notificationId {
        Objects.requireNonNull(value, "NotificationId cannot be null");
    }

    public static notificationId newId() {
        return new notificationId(UUID.randomUUID());
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
