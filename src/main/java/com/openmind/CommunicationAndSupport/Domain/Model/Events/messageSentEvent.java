package com.openmind.CommunicationAndSupport.Domain.Model.Events;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.messageId;

public record messageSentEvent(conversationId conversationId, messageId messageId) {
}
