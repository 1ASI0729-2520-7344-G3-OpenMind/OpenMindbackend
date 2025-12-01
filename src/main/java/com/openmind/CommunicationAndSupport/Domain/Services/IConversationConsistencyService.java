package com.openmind.CommunicationAndSupport.Domain.Services;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;

public interface IConversationConsistencyService {
    void validateBeforeAddMessage(conversation conversation, String senderId);
}
