package com.openmind.CommunicationAndSupport.Application.Internal.OutbodunServices;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.chatbotResponse;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IChatbotExternalClient;
import com.openmind.CommunicationAndSupport.Domain.Services.IChatbotDomainService;
import org.springframework.stereotype.Service;

@Service
public class chatbotDomainServiceImpl implements IChatbotDomainService {
    private final IChatbotExternalClient externalClient;

    public chatbotDomainServiceImpl(IChatbotExternalClient externalClient) {
        this.externalClient = externalClient;
    }

    @Override
    public chatbotResponse ask(String conversationId, String text, String context) {
        return externalClient.ask(conversationId, text, context);
    }
}
