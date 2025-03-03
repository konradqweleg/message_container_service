package com.example.messagecontainer.adapter.out.friend_service;

import com.example.messagecontainer.entity.dto.FriendPairDTO;
import com.example.messagecontainer.entity.dto.IsFriendsDTO;
import com.example.messagecontainer.exception.friend_service.FriendDataParsingException;
import com.example.messagecontainer.exception.friend_service.GeneralFriendServiceException;
import com.example.messagecontainer.exception.friend_service.InvalidFriendRequestException;
import com.example.messagecontainer.port.out.FiendServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class FriendServiceAdapter implements FiendServicePort {

    private final URI uriRequestIsFriends = new URI("http://friend-service:8089/friendsService/api/v1/friends/isFriends");

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LogManager.getLogger(FriendServiceAdapter.class);

    public FriendServiceAdapter() throws URISyntaxException {
    }

    @Override
    public Mono<IsFriendsDTO> isFriends(FriendPairDTO friendsIds) {
        String uri = UriComponentsBuilder.fromUri(uriRequestIsFriends)
                .queryParam("friendFirstId", friendsIds.idFirstFriend())
                .queryParam("friendSecondId", friendsIds.idSecondFriend())
                .toUriString();

        return WebClient.create().get().uri(uri)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> {
                            logger.error("Bad request for friendship status: {}", response.statusCode());
                            return Mono.error(new InvalidFriendRequestException("Error retrieving friendship status, bad request")
                            );
                        }
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        IsFriendsDTO isFriendsDTO = objectMapper.readValue(responseEntity.getBody(), IsFriendsDTO.class);
                        return Mono.just(isFriendsDTO);
                    } catch (JsonProcessingException e) {
                        logger.error("Error parsing friendship status response: {}", e.getMessage());
                        return Mono.error(new FriendDataParsingException("Error retrieving friendship status, parsing error"));
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Error retrieving friendship status: {}", e.getMessage());
                    return Mono.error(new GeneralFriendServiceException("Error retrieving friendship status"));
                });
    }
}
