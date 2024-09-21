package com.example.messagecontainer.adapter.in.rest;

import com.example.messagecontainer.adapter.in.rest.util.ConvertToJSON;
import com.example.messagecontainer.entity.request.IdUserData;
import com.example.messagecontainer.entity.request.MainUserRequest;
import com.example.messagecontainer.entity.request.MessageData;
import com.example.messagecontainer.port.in.MessagePort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/messageService/api/v1/message")
public class MessageController {

    private final MessagePort messagePort;

    public MessageController(MessagePort messagePort) {
        this.messagePort = messagePort;
    }

    @PostMapping("/insertMessage")
    public Mono<ResponseEntity<String>> createFriends(@RequestBody @Valid Mono<MessageData> messageDataMono) {
        return messagePort.insertMessage(messageDataMono).flatMap(ConvertToJSON::convert);
    }

    @GetMapping("/getLastMessagesWithFriendsForUser")
    public Mono<ResponseEntity<String>> getLastMessagesWithFriendForUser(@RequestParam Long idUser) {
        return ConvertToJSON.convert(messagePort.getLastMessagesWithFriendsForSpecificUser( Mono.just(new IdUserData(idUser)))) ;
    }

    @GetMapping("/getMessageBetweenUsers")
    public Mono<ResponseEntity<String>> getMessageBetweenUsers(@RequestParam Long idFirstUser, @RequestParam Long idFriend) {
        return ConvertToJSON.convert(messagePort.getMessageBetweenUsers(Mono.just(new IdUserData(idFirstUser)), Mono.just(new IdUserData(idFriend))));
    }

    @PostMapping("/getMessagesWithFriendsFromId")
    public Mono<ResponseEntity<String>> getMessagesWithFriendsFromId(@RequestBody @Valid Mono<MainUserRequest> mainUserRequestMono) {
        return ConvertToJSON.convert(messagePort.getMessagesWithFriendsFromId(mainUserRequestMono));
    }
}
