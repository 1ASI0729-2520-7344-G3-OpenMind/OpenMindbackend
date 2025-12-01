package com.openmind.CommunicationAndSupport.Domain.Model.Events;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.chatbotResponse;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;

public record chatbotRepliedEvent(conversationId conversationId, chatbotResponse response) {
}
