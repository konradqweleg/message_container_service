package com.example.messagecontainer.port.out;

import com.example.messagecontainer.entity.request.IdUserDTO;
import com.example.messagecontainer.entity.request.UserData;
import reactor.core.publisher.Mono;

public interface UserServicePort {
    Mono<UserData> getUserAboutId(IdUserDTO idUserDTOMono);
}
