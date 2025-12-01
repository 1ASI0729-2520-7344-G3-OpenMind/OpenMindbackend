package com.openmind.CommunicationAndSupport.Interfaces.REST.Transform;

import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Resources.chatbotReplyResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class messageResourceAssembler {
    public chatbotReplyResource toResource(message entity){
        return new chatbotReplyResource(
                entity.id().toString(),
                entity.conversationId().toString(),
                entity.sender().name(),
                entity.content().value(),
                entity.sentAt().value()
        );
    }

    public List<chatbotReplyResource> toResourceList(List<message> messages){
        return messages.stream().map(this::toResource).collect(Collectors.toList());
    }
}
