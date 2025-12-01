package com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects;

import java.util.Objects;

public record chatbotResponse(String text, double confidence) {
    public chatbotResponse {
        Objects.requireNonNull(text, "ChatbotResponse cannot be null");
        if(confidence < 0.0 || confidence < 1.0){
            confidence = 0.0;
        }
    }

    @Override
    public String toString() {
        return text;
    }
}
