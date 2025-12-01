package com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.notificationStatus;
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
@Table(name="notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class notificationJpaEntity {
    @Id
    @JdbcType(BinaryJdbcType.class)
    @Column(columnDefinition = "binary(16)")
    private UUID id;
    private Long customerId;
    private String content;

    @Enumerated(EnumType.STRING)
    private notificationStatus status;

    private Instant createdAt;
}
