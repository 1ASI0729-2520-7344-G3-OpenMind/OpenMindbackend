package com.openmind.CommunicationAndSupport.Domain.Model.Aggregates;

import com.openmind.CommunicationAndSupport.Domain.Exceptions.messageCreationException;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.*;

import java.time.Instant;
import java.util.*;

public class conversation {
    private final conversationId id;
    private final customerId customerId;
    private boolean active;
    private final timestamp createdAt;
    private timestamp updatedAt;
    private List<message> messages = new ArrayList<>();

    public conversation(conversationId id, customerId customerId, timestamp createdAt){
        this.id = Objects.requireNonNull(id, "ConversationId cannot be null");
        this.customerId = Objects.requireNonNull(customerId, "CustomerId cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt cannot be null");
        this.updatedAt = createdAt;
        this.active = true;
    }

    public conversation(conversationId id, customerId customerId, boolean active, timestamp createdAt, timestamp updatedAt, List<message> messages){
        this.id = Objects.requireNonNull(id, "ConversationId cannot be null");
        this.customerId = Objects.requireNonNull(customerId, "CustomerId cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "UpdatedAt cannot be null");
        this.active = active;
        this.messages = Objects.requireNonNull(messages, "Messages list cannot be null");
    }

    public conversationId id(){
        return id;
    }

    public customerId customerId(){
        return customerId;
    }

    public  timestamp createdAt(){
        return createdAt;
    }

    public timestamp updatedAt(){
        return updatedAt;
    }

    public List<message> messages(){
        return Collections.unmodifiableList(messages);
    }

    public message addMessage(senderType sender, messageContent content){
        message msg = new message(
                new messageId(UUID.randomUUID()),
                this.id,
                sender,
                content,
                timestamp.now()
        );
        this.messages.add(msg);
        msg.setConversation(this);
        return msg;
    }

    public void addExistingMessage(message message){
        if(!active){
            throw new messageCreationException("Cannot add message to closed conversation");
        }
        messages.add(Objects.requireNonNull(message));
    }

    public void close(){
        this.active = false;
        this.updatedAt = timestamp.now();
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }


}
