package com.openmind.CommunicationAndSupport.Interfaces.REST;

import com.openmind.CommunicationAndSupport.Application.Internal.CommandServices.sendNotificationCommandService;
import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.notificationAggregate;
import com.openmind.CommunicationAndSupport.Domain.Model.Commands.sendNotificationCommand;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Resources.notificationResource;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Transform.notificationResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class notificationController {
    private final sendNotificationCommandService commandService;
    private final notificationResourceAssembler assembler;

    public  notificationController(sendNotificationCommandService commandService, notificationResourceAssembler resourceAssembler) {
        this.commandService = commandService;
        this.assembler = resourceAssembler;
    }

    @PostMapping
    public ResponseEntity<notificationResource> sendNotification(@RequestBody notificationResource resource) {
        sendNotificationCommand command = new sendNotificationCommand(resource.customerId(), resource.content());
        notificationAggregate aggregate = commandService.handle(command);
        notificationResource responseResource = assembler.toResource(aggregate);
        return new ResponseEntity<>(responseResource, HttpStatus.ACCEPTED);
    }
}
