package com.openmind.CommunicationAndSupport.Application.Internal.CommandServices;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.notificationAggregate;
import com.openmind.CommunicationAndSupport.Domain.Model.Commands.sendNotificationCommand;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.notification;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.customerId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.notificationContent;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.notificationId;
import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.timestamp;
import com.openmind.CommunicationAndSupport.Domain.Repositories.INotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class sendNotificationCommandService {
    private final INotificationRepository notificationRepository;

    public sendNotificationCommandService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public notificationAggregate handle(sendNotificationCommand command){
        notification n = new notification(notificationId.newId(), new customerId(command.customerId()), new notificationContent(command.content()), timestamp.now());
        notificationAggregate aggregate = new notificationAggregate(n);
        return notificationRepository.save(aggregate);
    }
}
