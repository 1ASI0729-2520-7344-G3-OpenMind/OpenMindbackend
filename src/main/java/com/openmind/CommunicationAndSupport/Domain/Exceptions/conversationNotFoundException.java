package com.openmind.CommunicationAndSupport.Domain.Exceptions;

public class conversationNotFoundException extends RuntimeException {
    public conversationNotFoundException(String id){
        super("Conversation not found: "+id);
    }
}
