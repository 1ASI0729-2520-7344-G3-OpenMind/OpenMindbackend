package com.openmind.CommunicationAndSupport.Domain.Services;

import com.openmind.CommunicationAndSupport.Domain.Model.Entities.notification;

public interface INotificationDomainService {
    notification createNotification(Long customerId, String content);
}
