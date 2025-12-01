package com.openmind.CommunicationAndSupport.Interfaces.REST.Transform;

import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.notificationAggregate;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.notification;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Resources.notificationResource;
import org.springframework.stereotype.Component;

@Component
public class notificationResourceAssembler {
    public notificationResource toResource(notificationAggregate aggregate){
        notification n = aggregate.getNotification();
        return new notificationResource(
                n.id().value(),
                n.customerId().value(),
                n.content().value(),
                n.status(),
                n.createdAt().value()
        );
    }
}
