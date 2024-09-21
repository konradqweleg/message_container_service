package com.example.messagecontainer.port.in;

import com.example.messagecontainer.entity.request.IdUserData;
import com.example.messagecontainer.entity.request.MainUserRequest;
import com.example.messagecontainer.entity.response.MessageData;

import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.entity.response.Status;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MessagePort {
    Mono<Result<Status>> insertMessage(Mono<com.example.messagecontainer.entity.request.MessageData> message);
    Flux<MessageData> getMessageBetweenUsers(Mono<IdUserData> idFirstUserMono, Mono<IdUserData> idFriendMono);
    Flux<MessageData> getLastMessagesWithFriendsForSpecificUser(Mono<IdUserData> idUserDataMono);
    Flux<MessageData> getMessagesWithFriendsFromId(Mono<MainUserRequest> lastMessage);

}
