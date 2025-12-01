package com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects;

import java.util.Objects;

public record messageContent(String value) {
    public messageContent{
        Objects.requireNonNull(value, "MessageContent cannot be null");
        if(value.isBlank()){
            throw new IllegalArgumentException("MessageContent cannot be blank");
        }
        if(value.length() > 5000){
            throw new IllegalArgumentException("MessageContent cannot be longer than 5000");
        }
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
