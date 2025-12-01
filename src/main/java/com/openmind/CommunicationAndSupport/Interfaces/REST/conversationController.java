package com.openmind.CommunicationAndSupport.Interfaces.REST;

import com.openmind.CommunicationAndSupport.Application.Internal.CommandServices.addMessageCommandService;
import com.openmind.CommunicationAndSupport.Application.Internal.CommandServices.createConversationCommandService;
import com.openmind.CommunicationAndSupport.Application.Internal.QueryServices.getConversationQueryService;
import com.openmind.CommunicationAndSupport.Application.Internal.QueryServices.getMessagesByConversationQueryService;
import com.openmind.CommunicationAndSupport.Domain.Model.Aggregates.conversation;
import com.openmind.CommunicationAndSupport.Domain.Model.Commands.addMessageToConversationCommand;
import com.openmind.CommunicationAndSupport.Domain.Model.Commands.createConversationCommand;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Domain.Model.Queries.getConversationByCustomerIdQuery;
import com.openmind.CommunicationAndSupport.Domain.Model.Queries.getConversationByIdQuery;
import com.openmind.CommunicationAndSupport.Domain.Model.Queries.getMessagesByConversationQuery;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Resources.addMessageResource;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Resources.chatbotReplyResource;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Resources.conversationResource;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Resources.createConversationResource;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Transform.conversationResourceAssembler;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Transform.messageResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conversations")
public class conversationController {
    private final createConversationCommandService createCommandService;
    private final addMessageCommandService addMessageCommandService;
    private final getConversationQueryService getQueryService;
    private final getMessagesByConversationQueryService getMessagesQueryService;

    private final conversationResourceAssembler conversationAssembler;
    private final messageResourceAssembler messageAssembler;

    public conversationController(createConversationCommandService createCommandService, addMessageCommandService addMessageCommandService, getConversationQueryService getConversationQueryService, getMessagesByConversationQueryService getMessagesByConversationQueryService, conversationResourceAssembler conversationResourceAssembler, messageResourceAssembler messageResourceAssembler) {
        this.createCommandService = createCommandService;
        this.addMessageCommandService = addMessageCommandService;
        this.getQueryService = getConversationQueryService;
        this.getMessagesQueryService = getMessagesByConversationQueryService;
        this.conversationAssembler = conversationResourceAssembler;
        this.messageAssembler = messageResourceAssembler;
    }

    @PostMapping
    public ResponseEntity<conversationResource> createConversation(@RequestBody createConversationResource resource){
        createConversationCommand command = new createConversationCommand(resource.customerId());
        conversation newConversation = createCommandService.handle(command);
        conversationResource responseResource = conversationAssembler.toResource(newConversation);
        return new ResponseEntity<>(responseResource, HttpStatus.CREATED);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<conversationResource> getConversationById(@PathVariable String conversationId){
        getConversationByIdQuery query = new getConversationByIdQuery(conversationId);
        conversation cnv = getQueryService.execute(query);
        conversationResource responseResource = conversationAssembler.toResource(cnv);
        return ResponseEntity.ok(responseResource);
    }

    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<chatbotReplyResource> addMessageToConversation(@PathVariable String conversationId, @RequestBody addMessageResource resource){
        addMessageToConversationCommand command = new addMessageToConversationCommand(conversationId, resource.sender(), resource.text());
        message newMessage = addMessageCommandService.handle(command);
        chatbotReplyResource responseResource = messageAssembler.toResource(newMessage);
        return new ResponseEntity<>(responseResource, HttpStatus.CREATED);
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<chatbotReplyResource>> getMessagesByConversation(@PathVariable String conversationId){
        getMessagesByConversationQuery query = new getMessagesByConversationQuery(conversationId);
        List<message> messages = getMessagesQueryService.execute(query);
        List<chatbotReplyResource> responseResource = messageAssembler.toResourceList(messages);
        return ResponseEntity.ok(responseResource);
    }

    @GetMapping
    public ResponseEntity<List<conversationResource>> getConversationsByCustomerId(@RequestParam Long customerId){
        getConversationByCustomerIdQuery query = new getConversationByCustomerIdQuery(customerId);
        List<conversation> conversations = getQueryService.execute(query);
        List<conversationResource> resource = conversationAssembler.toResourceList(conversations);
        return ResponseEntity.ok(resource);
    }
}
