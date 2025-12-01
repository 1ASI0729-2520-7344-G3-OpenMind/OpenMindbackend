package com.openmind.CommunicationAndSupport.Interfaces.REST;

import com.openmind.CommunicationAndSupport.Application.Internal.CommandServices.processChatbotReplyCommandService;
import com.openmind.CommunicationAndSupport.Domain.Model.Entities.message;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Resources.chatbotReplyResource;
import com.openmind.CommunicationAndSupport.Interfaces.REST.Transform.messageResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internal/chatbot")
public class chatbotController {
    private final processChatbotReplyCommandService commandService;
    private final messageResourceAssembler assembler;

    public chatbotController(processChatbotReplyCommandService commandService, messageResourceAssembler assembler) {
        this.commandService = commandService;
        this.assembler = assembler;
    }

    @PostMapping("/reply")
    public ResponseEntity<chatbotReplyResource> registerChatbotReply(@RequestParam String conversationId, @RequestBody String replyText){
        message botMessage = commandService.handle(conversationId, replyText);
        chatbotReplyResource responseResource = assembler.toResource(botMessage);
        return new  ResponseEntity<>(responseResource, HttpStatus.CREATED);
    }
}
