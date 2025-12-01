package com.openmind.CommunicationAndSupport.Domain.Exceptions;

public class messageCreationException extends RuntimeException {
    public messageCreationException(String reason) {
        super("Message creation failed: "+reason);
    }
}
