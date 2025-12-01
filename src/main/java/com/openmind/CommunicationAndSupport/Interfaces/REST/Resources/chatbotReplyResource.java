package com.openmind.CommunicationAndSupport.Interfaces.REST.Resources;

import java.time.Instant;

public record chatbotReplyResource(String id, String conversationId, String sender, String content, Instant sentAt) {
}
