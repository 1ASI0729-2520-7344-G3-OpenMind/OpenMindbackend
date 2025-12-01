package com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects;

import java.util.Objects;

public record notificationContent(String value) {
    public notificationContent{
        Objects.requireNonNull(value, "NotificationContent cannot be null");
        if(value.isBlank()){
            throw new IllegalArgumentException("NotificationContent cannot be blank");
        }
    }

    @Override
    public String toString(){
        return value;
    }
}
