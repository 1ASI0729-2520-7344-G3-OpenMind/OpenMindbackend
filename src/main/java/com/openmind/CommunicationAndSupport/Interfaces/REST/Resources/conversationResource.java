package com.openmind.CommunicationAndSupport.Interfaces.REST.Resources;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record conversationResource(UUID id,Long customerId, boolean active, Instant createdAt, Instant sentAt, List<chatbotReplyResource> messages) {
}
