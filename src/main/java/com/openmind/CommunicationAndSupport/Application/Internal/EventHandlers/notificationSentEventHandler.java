package com.openmind.CommunicationAndSupport.Application.Internal.EventHandlers;

import com.openmind.CommunicationAndSupport.Domain.Exceptions.notificationException;
import com.openmind.CommunicationAndSupport.Domain.Model.Events.notificationSentEvent;
import com.openmind.CommunicationAndSupport.Domain.Repositories.INotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class notificationSentEventHandler {
    private final INotificationRepository notificationRepository;

    public notificationSentEventHandler(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @EventListener
    public void handle(notificationSentEvent event){
        var notif = notificationRepository.findById(event.notificationId()).orElseThrow(()->new notificationException("notification not found: "+event.notificationId()));
        notif.markAsSent();
        notificationRepository.save(notif);
    }
}
