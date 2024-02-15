package com.example.messagecontainer.repository;

import com.example.messagecontainer.entity.LastMessageUserTag;
import com.example.messagecontainer.model.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MessageRepository extends ReactiveCrudRepository<Message, Long>  {
    Flux<Message> getUserMessagesSinceTag(LastMessageUserTag lastMessageUserTag);
    Mono<Void> insertUserMessage(Message message);
}
