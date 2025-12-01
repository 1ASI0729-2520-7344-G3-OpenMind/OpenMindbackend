package com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Repositories;

import com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities.notificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface INotificationJpaRepository extends JpaRepository<notificationJpaEntity, UUID> {
    List<notificationJpaEntity> findByCustomerId(Long conversationId);
}
