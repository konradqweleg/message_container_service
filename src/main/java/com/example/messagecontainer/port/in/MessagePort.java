package com.example.messagecontainer.port.in;

import com.example.messagecontainer.entity.request.MessageData;
import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.entity.response.Status;
import reactor.core.publisher.Mono;



public interface MessagePort {

    Mono<Result<Status>> insertMessage(Mono<MessageData> message);
}
