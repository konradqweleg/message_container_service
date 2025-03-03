package com.example.messagecontainer.port.out;

import com.example.messagecontainer.model.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RepositoryPort {
    Mono<Message> insertMessage(Message message);
    Flux<Message> findLastMessagesWithFriendsByUserId(Long idUser);

    Flux<Message> findAllMessagesBetweenUsers(Long idFirstUser, Long idSecondUser);

    Flux<Message> findAllMessagesBetweenUsersSinceMessageId(Long idFirstUser, Long idSecondUser, Long messageId);

}
