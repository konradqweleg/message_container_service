package com.example.messagecontainer.port.out;

import com.example.messagecontainer.entity.request.FriendData;
import com.example.messagecontainer.entity.request.IsFriends;
import com.example.messagecontainer.entity.response.Result;
import reactor.core.publisher.Mono;

public interface FiendServicePort {
    Mono<Result<IsFriends>> isFriends(Mono<FriendData> friendsIds);
}
