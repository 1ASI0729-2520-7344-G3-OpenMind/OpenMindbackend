package com.openmind.CommunicationAndSupport.Domain.Repositories;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;

import java.util.List;
import java.util.Optional;

public interface IConversationRepository {
    conversation save(conversation conversation);
    Optional<conversation> findById(conversationId id);
    List<conversation> findByCustomerId(Long customerId);
}
