package com.openmind.CommunicationAndSupport.Domain.Model.Commands;

public record addMessageToConversationCommand(String conversationId, String sender, String text) {
}
