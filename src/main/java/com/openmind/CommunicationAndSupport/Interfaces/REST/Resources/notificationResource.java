package com.openmind.CommunicationAndSupport.Interfaces.REST.Resources;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.notificationStatus;

import java.time.Instant;
import java.util.UUID;

public record notificationResource(UUID id, Long customerId, String content, notificationStatus status, Instant createdAt) {
}
