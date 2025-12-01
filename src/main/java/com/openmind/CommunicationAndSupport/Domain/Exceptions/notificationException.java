package com.openmind.CommunicationAndSupport.Domain.Exceptions;

public class notificationException extends RuntimeException {
    public notificationException(String reason) {
        super("Notification error: "+reason);
    }
}
