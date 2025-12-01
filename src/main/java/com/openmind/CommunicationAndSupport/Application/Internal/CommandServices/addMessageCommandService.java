package com.openmind.CommunicationAndSupport.Application.Internal.CommandServices;

import com.openmind.CommunicationAndSupport.Domain.Exceptions.conversationNotFoundException;
import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.Commands.addMessageToConversationCommand;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Domain.Model.Events.messageSentEvent;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.chatbotResponse;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.messageContent;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.senderType;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IConversationRepository;
import com.openmind.CommunicationAndSupport.Domain.Services.IChatbotDomainService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class addMessageCommandService {
    private final IConversationRepository conversationRepository;
    private final IChatbotDomainService chatbotService;
    private final ApplicationEventPublisher eventPublisher;
    private final Logger logger = LoggerFactory.getLogger(addMessageCommandService.class);
    public addMessageCommandService(IConversationRepository conversationRepository, IChatbotDomainService chatbotService, ApplicationEventPublisher eventPublisher) {
        this.conversationRepository = conversationRepository;
        this.chatbotService = chatbotService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public message handle(addMessageToConversationCommand command){
        String conversationIdStr = command.conversationId();
        message botmsg = null;
        try{
            conversationId id = new conversationId(UUID.fromString(conversationIdStr));
            conversation cnv = conversationRepository.findById(id).orElseThrow(()-> new conversationNotFoundException(command.conversationId()));
            message msg = cnv.addMessage(senderType.valueOf(command.sender()), new messageContent(command.text()));
            if(senderType.valueOf(command.sender()) == senderType.CUSTOMER){
                String text = msg.content().toString();
                logger.info("llamando a gemini con: {}", text);
                chatbotResponse response = chatbotService.ask(id.toString(), text, "You are a helpful support bot");
                botmsg = cnv.addMessage(senderType.BOT, new messageContent(response.text()));
                logger.info("Respues del bot guardada: {}", response.text());
            }
            conversationRepository.save(cnv);
            return botmsg != null ? botmsg:msg;
        }catch(Exception e){
            System.err.println("!!!!!!!!!!!!!!!!!! FATAL GEMINI ERROR !!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            logger.error("Gemini call FAILED during sync process for {}: {}", conversationIdStr, e.getMessage());
            throw new RuntimeException("AI Communication Error for conversation " + conversationIdStr, e);
        }
    }
}
