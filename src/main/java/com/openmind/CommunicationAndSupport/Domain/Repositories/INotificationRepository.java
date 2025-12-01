package com.openmind.CommunicationAndSupport.Domain.Repositories;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.notificationAggregate;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.notificationId;

import java.util.List;
import java.util.Optional;

public interface INotificationRepository {
    notificationAggregate save(notificationAggregate notification);
    Optional<notificationAggregate> findById(notificationId id);
    List<notificationAggregate> findByRecipientId(String recipientId);
}
