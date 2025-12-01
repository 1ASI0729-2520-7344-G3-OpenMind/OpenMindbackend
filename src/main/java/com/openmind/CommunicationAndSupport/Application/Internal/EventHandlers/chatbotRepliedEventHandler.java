package com.openmind.CommunicationAndSupport.Application.Internal.EventHandlers;

import com.openmind.CommunicationAndSupport.Domain.Exceptions.conversationNotFoundException;
import com.openmind.CommunicationAndSupport.Domain.Model.Events.chatbotRepliedEvent;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.messageContent;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.senderType;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IConversationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class chatbotRepliedEventHandler {
    private final IConversationRepository conversationRepository;

    public chatbotRepliedEventHandler(IConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
    public void handle(chatbotRepliedEvent event){
        var conversation = conversationRepository.findById(event.conversationId()).orElseThrow(()->new conversationNotFoundException(event.conversationId().toString()));
        conversation.addMessage(senderType.BOT, new messageContent(event.response().text()));
        conversationRepository.save(conversation);
    }
}
