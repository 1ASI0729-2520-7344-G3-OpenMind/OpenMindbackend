package com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Repositories;

import com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities.conversationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IConversationJpaRepository extends JpaRepository<conversationJpaEntity, UUID> {
    List<conversationJpaEntity> findByCustomerId(Long conversationId);
}
