package com.example.messagecontainer.port.out;

import com.example.messagecontainer.model.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DatabasePort {
    Mono<Message> insertMessage(Message message);
    Flux<Message> getLastMessagesWithFriendForUser(Long idUser);

    Flux<Message> getAllMessagesBetweenUser(Long idFirstUser,Long idSecondUser);

    Flux<Message> getAllMessagesBetweenUserSinceId(Long idFirstUser, Long idSecondUser, Long messageId);

}
