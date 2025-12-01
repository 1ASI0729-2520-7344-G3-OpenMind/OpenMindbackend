package com.openmind.CommunicationAndSupport.Application.Internal.EventHandlers;

import com.openmind.CommunicationAndSupport.Domain.Exceptions.conversationNotFoundException;
import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Domain.Model.Events.messageSentEvent;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.chatbotResponse;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.messageContent;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.senderType;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IConversationRepository;
import com.openmind.CommunicationAndSupport.Domain.Services.IChatbotDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class messageSentEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(messageSentEventHandler.class);

    private final IConversationRepository repository;
    private final IChatbotDomainService chatbotService;

    public messageSentEventHandler(IConversationRepository repository, IChatbotDomainService chatbotService) {
        this.repository = repository;
        this.chatbotService = chatbotService;
    }

    @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
    public void handle(messageSentEvent event) throws Exception{
        conversationId id = event.conversationId();
        try{
            conversation cnv = repository.findById(id).orElseThrow(()->new conversationNotFoundException(id.toString()));
            message last = cnv.messages().stream().reduce((a,b)->b).orElseThrow(()->new IllegalStateException("Conversation has no messages to process for AI."));
            String messageText = last.content().toString();
            logger.info("Calling AI with text: {}", messageText);
            chatbotResponse response = chatbotService.ask(id.toString(), messageText,"You are a helpful support bot.");
            cnv.addMessage(senderType.BOT, new messageContent(response.text()));
            repository.save(cnv);
            logger.info("AI response processed and saved successfully for conversation: {}", id.toString());
        }catch (conversationNotFoundException e){
            logger.error("AI procesing failed: Conversation {} not found", id.toString());
        }catch(Exception e){
            logger.error("AI communication failed for conversation {}: {}", id.toString(), e.getMessage(),e);
        }
    }
}
