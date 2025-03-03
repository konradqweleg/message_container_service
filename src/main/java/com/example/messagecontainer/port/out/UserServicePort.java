package com.example.messagecontainer.port.out;

import com.example.messagecontainer.entity.dto.IdUserDTO;
import com.example.messagecontainer.entity.dto.UserDataDTO;
import reactor.core.publisher.Mono;

public interface UserServicePort {
    Mono<UserDataDTO> getUserAboutId(IdUserDTO idUserDTOMono);
}
