package com.openmind.CommunicationAndSupport.Domain.Repositories;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.chatbotResponse;

public interface IChatbotExternalClient {
    chatbotResponse ask(String conversationId, String text, String context);
}
