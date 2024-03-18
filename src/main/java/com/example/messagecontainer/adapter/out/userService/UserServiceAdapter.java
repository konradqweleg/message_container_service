package com.example.messagecontainer.adapter.out.userService;

import com.example.messagecontainer.entity.request.IdUserData;
import com.example.messagecontainer.entity.request.UserData;
import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.port.out.UserServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UserServiceAdapter implements UserServicePort {

    private final URI uriGetUserAboutId = new URI("http://localhost:8082/userServices/api/v1/user/getUserAboutId/");

    private final ObjectMapper objectMapper = new ObjectMapper();


    public UserServiceAdapter() throws URISyntaxException {
    }

    @Override
    public Mono<Result<UserData>> getUserAboutId(Mono<IdUserData> idUserDataMono) {

        return idUserDataMono.flatMap(

                idUserData -> WebClient.create().get().uri(uriGetUserAboutId + idUserData.idUser().toString())
                        .retrieve()
                        .onStatus(
                                HttpStatus.BAD_REQUEST::equals,
                                response -> response.bodyToMono(String.class).map(Exception::new)
                        )
                        .toEntity(String.class)
                        .flatMap(responseEntity -> {
                            try {
                                UserData userData =  objectMapper.readValue(responseEntity.getBody(), UserData.class);
                                return Mono.just(Result.success(userData));
                            } catch (JsonProcessingException e) {
                                return Mono.error(new RuntimeException(e));
                            }

                        })
                        .onErrorResume(response -> Mono.just(Result.<UserData>error(response.getMessage()))
        )

        );
    }


}
