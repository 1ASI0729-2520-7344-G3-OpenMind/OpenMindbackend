package com.openmind.CommunicationAndSupport.Application.Internal.QueryServices;

import com.openmind.CommunicationAndSupport.Domain.Exceptions.conversationNotFoundException;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Domain.Model.Queries.getMessagesByConversationQuery;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class getMessagesByConversationQueryService {
    private final IConversationRepository conversationRepository;

    public getMessagesByConversationQueryService(IConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public List<message> execute(getMessagesByConversationQuery query){
        try{
            conversationId id = new conversationId(UUID.fromString(query.conversationId()));
            return conversationRepository.findById(id).map(conversation -> conversation.messages()).orElseThrow(()->new conversationNotFoundException(query.conversationId()));
        }catch(IllegalArgumentException e){
            throw new conversationNotFoundException("Invalid ID format: "+query.conversationId());
        }
    }
}
