package com.openmind.CommunicationAndSupport.Domain.Model.Events;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.notificationId;

public record notificationSentEvent(notificationId notificationId) {
}
