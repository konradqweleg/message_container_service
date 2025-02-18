package com.example.messagecontainer.adapter.in.rest;

import com.example.messagecontainer.adapter.in.rest.util.ResponseUtil;
import com.example.messagecontainer.entity.request.IdUserDTO;
import com.example.messagecontainer.entity.request.MainUserRequest;
import com.example.messagecontainer.entity.request.MessageDTO;
import com.example.messagecontainer.entity.response.MessageData;
import com.example.messagecontainer.port.in.MessagePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/messages")
public class MessageController {

    private final MessagePort messagePort;

    public MessageController(MessagePort messagePort) {
        this.messagePort = messagePort;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createMessage(@RequestBody @Valid MessageDTO messageDataMono) {
        return ResponseUtil.toResponseEntity(messagePort.insertMessage(messageDataMono), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/friends/last-messages")
    public Mono<ResponseEntity<List<MessageData>>> getLastMessagesWithFriends(@PathVariable("userId") Long userId) {
        return ResponseUtil.toResponseEntity(messagePort.getLastMessagesWithFriendsForSpecificUser(new IdUserDTO(userId)), HttpStatus.OK);
    }

    @GetMapping("/{firstUserId}/friends/{friendId}/messages")
    public Mono<ResponseEntity<List<MessageData>>> getMessagesBetweenUsers(@PathVariable("firstUserId") Long firstUserId, @PathVariable("friendId") Long friendId) {
        return ResponseUtil.toResponseEntity(messagePort.getMessageBetweenUsers(new IdUserDTO(firstUserId), new IdUserDTO(friendId)), HttpStatus.OK);
    }

    @PostMapping("/{userId}/friends/messages")
    public Mono
            <ResponseEntity<List<MessageData>>> getMessagesWithFriends(@PathVariable("userId") Long userId, @RequestBody @Valid MainUserRequest mainUserRequestMono) {
        return ResponseUtil.toResponseEntity(messagePort.getMessagesWithFriendsFromId(new IdUserDTO(userId), mainUserRequestMono), HttpStatus.OK);
    }
}
