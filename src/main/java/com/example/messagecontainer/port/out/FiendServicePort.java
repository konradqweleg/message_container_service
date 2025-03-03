package com.example.messagecontainer.port.out;

import com.example.messagecontainer.entity.dto.FriendPairDTO;
import com.example.messagecontainer.entity.dto.IsFriendsDTO;
import reactor.core.publisher.Mono;

public interface FiendServicePort {
    Mono<IsFriendsDTO> isFriends(FriendPairDTO friendsIds);
}
