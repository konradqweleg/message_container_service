package com.example.messagecontainer.port.out;

import com.example.messagecontainer.entity.request.FriendPairDTO;
import com.example.messagecontainer.entity.request.IsFriends;
import reactor.core.publisher.Mono;

public interface FiendServicePort {
    Mono<IsFriends> isFriends(FriendPairDTO friendsIds);
}
