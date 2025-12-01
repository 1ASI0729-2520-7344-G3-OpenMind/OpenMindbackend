package com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.senderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="messages")
@Getter
@Setter
@NoArgsConstructor
public class messageJpaEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="conversation_id", nullable = false)
    private conversationJpaEntity conversation;

    @Enumerated(EnumType.STRING)
    private senderType sender;

    @Column(name="content", columnDefinition = "TEXT")
    private String content;

    private Instant sentAt;

    public messageJpaEntity(UUID id, senderType sender, String content, Instant sentAt) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.sentAt = sentAt;
    }
}
