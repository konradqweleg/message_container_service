package com.example.messagecontainer.port.out;

import com.example.messagecontainer.entity.request.IdUserData;
import com.example.messagecontainer.entity.request.UserData;
import com.example.messagecontainer.entity.response.Result;
import reactor.core.publisher.Mono;

public interface UserServicePort {
    Mono<Result<UserData>> getUserAboutId(Mono<IdUserData> idUserDataMono);
}
