package com.example.messagecontainer.port.out;

import com.example.messagecontainer.entity.LastMessageUserTag;
import com.example.messagecontainer.model.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DatabasePort {
//    Flux<Message> getUserMessagesSinceTag(LastMessageUserTag lastMessageUserTag);
    Mono<Message> insertMessage(Message message);
    Flux<Message> getLastMessagesWithFriendForUser(Long idUser);
//    Flux<Message> getLastMessagesForUsers();
}
