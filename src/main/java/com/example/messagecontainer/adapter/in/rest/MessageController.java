package com.example.messagecontainer.adapter.in.rest;

import com.example.messagecontainer.adapter.in.rest.util.ConvertToJSON;
import com.example.messagecontainer.entity.request.MessageData;
import com.example.messagecontainer.port.in.MessagePort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/messageService/api/v1/message")
public class MessageController {

    private MessagePort messagePort;

    public MessageController(MessagePort messagePort) {
        this.messagePort = messagePort;
    }

    @PostMapping("/insertMessage")
    public Mono<ResponseEntity<String>> createFriends(@RequestBody @Valid Mono<MessageData> messageDataMono) {
        return messagePort.insertMessage(messageDataMono).flatMap(ConvertToJSON::convert);
    }
}
