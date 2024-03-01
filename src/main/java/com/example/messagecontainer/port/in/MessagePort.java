package com.example.messagecontainer.port.in;

import com.example.messagecontainer.entity.request.IdUserData;
import com.example.messagecontainer.entity.request.MessageData;
import com.example.messagecontainer.entity.response.MessageResponse;
import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.entity.response.Status;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface MessagePort {
    Mono<Result<Status>> insertMessage(Mono<MessageData> message);
    Flux<MessageResponse> getLastMessagesWithFriendForUser(Mono<IdUserData> idUserDataMono);


}
