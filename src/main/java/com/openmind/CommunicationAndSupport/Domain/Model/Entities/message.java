package com.openmind.CommunicationAndSupport.Domain.Model.Entities;


import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class message {
    private messageId id;
    private conversationId conversationId;
    private senderType sender;
    private messageContent content;
    private timestamp sentAt;

    private final List<message> messages = new ArrayList<>();

    public message(messageId id, conversationId conversationId, senderType sender, messageContent content, timestamp sentAt) {
        this.id = Objects.requireNonNull(id, "MessageId cannot be null");
        this.conversationId = Objects.requireNonNull(conversationId, "ConversationId cannot be null");
        this.sender = Objects.requireNonNull(sender, "Sender cannot be null");
        this.content = Objects.requireNonNull(content, "Content cannot be null");
        this.sentAt = Objects.requireNonNull(sentAt, "SentAt cannot be null");
    }

    public messageId id(){
        return id;
    }

    public conversationId conversationId(){
        return conversationId;
    }

    public senderType sender(){
        return sender;
    }

    public messageContent content(){
        return content;
    }

    public timestamp sentAt(){
        return sentAt;
    }

    public void setConversation(conversation conversation) {
        this.conversationId = conversation.id();
    }

    public List<message> messages() {
        return Collections.unmodifiableList(messages);
    }

    public void setConversationId(conversationId conversationId) {
        this.conversationId = conversationId;
    }
}
