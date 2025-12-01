package com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class conversationJpaEntity {
    @Id
    @JdbcType(BinaryJdbcType.class)
    @Column(columnDefinition = "binary(16)")
    private UUID id;
    private Long customerId;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("sentAt ASC")
    private List<messageJpaEntity> messages;
}
