package com.example.messagecontainer.port.in;

import com.example.messagecontainer.entity.request.IdUserDTO;
import com.example.messagecontainer.entity.request.MainUserRequest;
import com.example.messagecontainer.entity.request.MessageDTO;
import com.example.messagecontainer.entity.response.MessageData;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MessagePort {
    Mono<Void> insertMessage(MessageDTO message);
    Flux<MessageData> getMessageBetweenUsers(IdUserDTO idFirstUserMono, IdUserDTO idFriendMono);
    Flux<MessageData> getLastMessagesWithFriendsForSpecificUser(IdUserDTO idUserDTOMono);
    Flux<MessageData> getMessagesWithFriendsFromId(IdUserDTO idUserDTO,MainUserRequest lastMessage);

}
