package com.openmind.CommunicationAndSupport.Application.Internal.CommandServices;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.Commands.createConversationCommand;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.customerId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.timestamp;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IConversationRepository;
import org.springframework.stereotype.Service;

@Service
public class createConversationCommandService {
    private final IConversationRepository conversationRepository;

    public createConversationCommandService(IConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public conversation handle(createConversationCommand command){
        conversation c = new conversation(conversationId.newId(), new customerId(command.customerId()), timestamp.now());
        return conversationRepository.save(c);
    }
}
