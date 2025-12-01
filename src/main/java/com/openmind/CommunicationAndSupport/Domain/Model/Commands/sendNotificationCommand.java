package com.openmind.CommunicationAndSupport.Domain.Model.Commands;

public record sendNotificationCommand(Long customerId, String content) {
}
