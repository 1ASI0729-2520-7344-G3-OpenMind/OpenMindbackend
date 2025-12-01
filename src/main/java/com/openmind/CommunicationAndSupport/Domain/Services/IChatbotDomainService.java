package com.openmind.CommunicationAndSupport.Domain.Services;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.chatbotResponse;

public interface IChatbotDomainService {
    chatbotResponse ask(String conversationId, String text, String context);
}
