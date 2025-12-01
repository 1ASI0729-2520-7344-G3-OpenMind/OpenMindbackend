package com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Repositories;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.notificationAggregate;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.notification;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.*;
import com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities.conversationJpaEntity;
import com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities.messageJpaEntity;
import com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities.notificationJpaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class mapperJpa {
    public message mapMessageToDomain(messageJpaEntity entity, conversationId conversationId) {
        message msg = new message(messageId.newId(), conversationId, entity.getSender(), new messageContent(entity.getContent()), new timestamp(entity.getSentAt()));
        msg.setConversationId(conversationId);
        return msg;
    }

    public messageJpaEntity mapToEntity(message domain){
        return new messageJpaEntity(domain.id().value(),domain.sender(), domain.content().value(), domain.sentAt().value());
    }

    public List<messageJpaEntity> mapMessageListToEntity(List<message> messages){
        return messages.stream().map(this::mapToEntity).collect(Collectors.toList());
    }

    public conversation mapToDomain(conversationJpaEntity entity){
        conversationId currentId = new conversationId(entity.getId());
        conversation c = new conversation(
                new conversationId(entity.getId()),
                new customerId(entity.getCustomerId()),
                entity.isActive(),
                new timestamp(entity.getCreatedAt()),
                new timestamp(entity.getUpdatedAt()),
                new ArrayList<>()
        );
        if(entity.getMessages() != null && !entity.getMessages().isEmpty()){
            entity.getMessages().stream().map(msgEntity->this.mapMessageToDomain(msgEntity, currentId)).forEach(c::addExistingMessage);
        }
        return c;
    }

    public notificationAggregate mapToDomain(notificationJpaEntity entity){
        notification n = new notification(
                new notificationId(entity.getId()),
                new customerId(entity.getCustomerId()),
                new notificationContent(entity.getContent()),
                new timestamp(entity.getCreatedAt())
        );
        notificationAggregate aggregate = new notificationAggregate(n);
        if(entity.getStatus() == notificationStatus.SENT){
            aggregate.markAsSent();
        }
        if(entity.getStatus() == notificationStatus.FAILED){
            aggregate.markAdFailed();
        }
        return aggregate;
    }

    public notificationJpaEntity mapToEntity(notificationAggregate domain){
        notification n = domain.getNotification();
        return new notificationJpaEntity(n.id().value(), n.customerId().value(), n.content().value(), n.status(), n.createdAt().value());
    }

    public List<messageJpaEntity> mapMessageListToEntityAndSetParent(List<message> messages, conversationJpaEntity parentEntity){
        return messages.stream().map(domainMsg -> {
            messageJpaEntity entityMsg = mapToEntity(domainMsg);
            entityMsg.setConversation(parentEntity);
            return entityMsg;
        }).collect(Collectors.toList());
    }


    public void updateEntity(conversationJpaEntity entity, conversation domain){
        entity.setActive(domain.isActive());
        entity.setCustomerId(domain.customerId().value());
        entity.setUpdatedAt(domain.updatedAt().value());
    }

    public conversationJpaEntity mapToEntity(conversation domain){
        conversationJpaEntity parentEntity = new conversationJpaEntity(
                domain.id().value(),
                domain.customerId().value(),
                domain.isActive(),
                domain.createdAt().value(),
                domain.updatedAt().value(),
                null
        );
        List<messageJpaEntity> synchronizedMessages = mapMessageListToEntityAndSetParent(
                domain.messages(),
                parentEntity
        );
        parentEntity.setMessages(synchronizedMessages);
        return parentEntity;
    }
}
