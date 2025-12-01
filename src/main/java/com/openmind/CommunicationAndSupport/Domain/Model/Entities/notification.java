package com.openmind.CommunicationAndSupport.Domain.Model.Entities;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.*;

import java.util.Objects;

public class notification {
    private final notificationId id;
    private final customerId customerId;
    private final notificationContent content;
    private notificationStatus status;
    private final timestamp createdAt;

    public notification(notificationId id, customerId customerId, notificationContent content, timestamp createdAt) {
        this.id = Objects.requireNonNull(id, "NotificationId cannot be null");
        this.customerId = Objects.requireNonNull(customerId, "CustomerId cannot be null");
        this.content = Objects.requireNonNull(content, "NotificationContent cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt cannot be null");
    }

    public notificationId id() {
        return id;
    }

    public customerId customerId() {
        return customerId;
    }

    public notificationContent content() {
        return content;
    }

    public notificationStatus status() {
        return status;
    }

    public timestamp createdAt() {
        return createdAt;
    }

    public void markSent(){
        this.status = notificationStatus.SENT;
    }

    public void markFailed(){
        this.status = notificationStatus.FAILED;
    }
}
