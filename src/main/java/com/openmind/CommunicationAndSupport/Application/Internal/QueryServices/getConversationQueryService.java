package com.openmind.CommunicationAndSupport.Application.Internal.QueryServices;

import com.openmind.CommunicationAndSupport.Domain.Exceptions.conversationNotFoundException;
import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.Queries.getConversationByCustomerIdQuery;
import com.openmind.CommunicationAndSupport.Domain.Model.Queries.getConversationByIdQuery;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class getConversationQueryService {
    private final IConversationRepository conversationRepository;
    public getConversationQueryService(IConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public conversation execute(getConversationByIdQuery query){
        try{
            conversationId id = new conversationId(UUID.fromString(query.conversationId()));
            return conversationRepository.findById(id).get();
        }catch(IllegalArgumentException e){
            throw new conversationNotFoundException("Invalid ID format: "+query.conversationId());
        }
    }

    public List<conversation> execute(getConversationByCustomerIdQuery query){
        return conversationRepository.findByCustomerId(query.customerId());
    }
}
