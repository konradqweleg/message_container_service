package com.example.messagecontainer.adapter.out.userService;

import com.example.messagecontainer.entity.request.IdUserDTO;
import com.example.messagecontainer.entity.request.UserData;
import com.example.messagecontainer.exception.user_service.GeneralUserServiceException;
import com.example.messagecontainer.exception.user_service.InvalidUserRequestException;
import com.example.messagecontainer.exception.user_service.UserDataParsingException;
import com.example.messagecontainer.exception.user_service.UserNotFoundException;
import com.example.messagecontainer.port.out.UserServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UserServiceAdapter implements UserServicePort {

    private final URI uriGetUserAboutId = new URI("http://user-service:8082/api/v1/users/");
    private final Logger logger = LogManager.getLogger(UserServiceAdapter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();


    public UserServiceAdapter() throws URISyntaxException {
    }

    @Override
    public Mono<UserData> getUserAboutId(IdUserDTO idUserDTO) {
        return WebClient.create().get()
                .uri(uriGetUserAboutId + idUserDTO.idUser().toString())
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, response -> {
                    logger.error("Received BAD REQUEST status for user with ID: {} , response {}", idUserDTO.idUser(), response);
                    return Mono.error(new InvalidUserRequestException("Error fetching user details"));
                })
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> {
                    logger.error("User with ID: {} not found", idUserDTO.idUser());
                    return Mono.error(new UserNotFoundException("User not found"));
                })
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        UserData userData = objectMapper.readValue(responseEntity.getBody(), UserData.class);
                        return Mono.just(userData);
                    } catch (JsonProcessingException e) {
                        logger.error("Error parsing user data for user with ID: {}", idUserDTO.idUser(), e);
                        return Mono.error(new UserDataParsingException("Error fetching user details"));
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Error fetching user details for user with ID: {}", idUserDTO.idUser(), e);
                    return Mono.error(new GeneralUserServiceException("Error fetching user details"));
                });
    }


}
