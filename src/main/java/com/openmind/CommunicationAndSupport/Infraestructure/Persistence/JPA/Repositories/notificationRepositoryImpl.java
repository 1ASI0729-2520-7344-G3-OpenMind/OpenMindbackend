package com.openmind.CommunicationAndSupport.Infraestructure.Persistence.JPA.Repositories;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.notificationAggregate;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.notificationId;
import com.openmind.CommunicationAndSupport.Domain.Repositories.INotificationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class notificationRepositoryImpl implements INotificationRepository {
    private final INotificationJpaRepository jpaRepository;
    private final mapperJpa mapper;

    public notificationRepositoryImpl(INotificationJpaRepository jpaRepository, mapperJpa mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public notificationAggregate save(notificationAggregate domain) {
        return mapper.mapToDomain(jpaRepository.save(mapper.mapToEntity(domain)));
    }

    @Override
    public Optional<notificationAggregate> findById(notificationId id){
        return jpaRepository.findById(id.value()).map(mapper::mapToDomain);
    }

    @Override
    public List<notificationAggregate> findByRecipientId(String recipientId){
        return jpaRepository.findByCustomerId(Long.valueOf(recipientId)).stream().map(mapper::mapToDomain).collect(Collectors.toList());
    }
}
