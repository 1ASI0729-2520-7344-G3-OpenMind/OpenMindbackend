package com.openmind.CommunicationAndSupport.Domain.Model.Aggregates;

import com.openmind.CommunicationAndSupport.Domain.Model.Entities.notification;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.notificationId;

import java.util.Objects;

public class notificationAggregate {
    private final notification notification;

    public notificationAggregate(notification notification) {
        this.notification = Objects.requireNonNull(notification);
    }

    public notification getNotification() {
        return notification;
    }
    public void markAsSent(){
        notification.markSent();
    }

    public void markAdFailed(){
        notification.markFailed();
    }
}
