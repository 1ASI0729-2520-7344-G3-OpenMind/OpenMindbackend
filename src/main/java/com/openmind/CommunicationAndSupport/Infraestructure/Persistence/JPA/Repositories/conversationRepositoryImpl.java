package com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Repositories;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.conversationId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.customerId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.timestamp;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IConversationRepository;
import com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Entities.conversationJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class conversationRepositoryImpl implements IConversationRepository {
    private final IConversationJpaRepository jpaRepository;
    private final mapperJpa mapper;

    public conversationRepositoryImpl(IConversationJpaRepository jpaRepository, mapperJpa mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public conversation save(conversation c) {
        conversationJpaEntity entity =  mapper.mapToEntity(c);
        conversationJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.mapToDomain(savedEntity);
    }

    @Override
    public Optional<conversation> findById (conversationId id){
        return jpaRepository.findById(id.value()).map(mapper::mapToDomain);
    }

    @Override
    public List<conversation> findByCustomerId(Long customerId){
        return jpaRepository.findByCustomerId(customerId).stream().map(mapper::mapToDomain).collect(Collectors.toList());
    }
}
