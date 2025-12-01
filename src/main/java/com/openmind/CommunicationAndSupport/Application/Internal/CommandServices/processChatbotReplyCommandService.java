package com.openmind.CommunicationAndSupport.Application.Internal.CommandServices;

import com.openmind.CommunicationAndSupport.Domain.Exceptions.conversationNotFoundException;
import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.messageContent;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.senderType;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IConversationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class processChatbotReplyCommandService {
    private final IConversationRepository conversationRepository;

    public processChatbotReplyCommandService(IConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public message handle(String conversationId, String replyText){
        conversationId id = new conversationId(UUID.fromString(conversationId));
        conversation cnv = conversationRepository.findById(id).orElseThrow(() -> new conversationNotFoundException(conversationId));
        message msg = cnv.addMessage(senderType.BOT, new messageContent(replyText));
        conversationRepository.save(cnv);
        return msg;
    }
}
