package com.example.messagecontainer.adapter.out.friend_service;

import com.example.messagecontainer.entity.request.FriendData;
import com.example.messagecontainer.entity.request.IsFriends;
import com.example.messagecontainer.entity.request.UserData;
import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.port.out.FiendServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class FriendAdapter implements FiendServicePort {

    private final URI uriRequestIsFriends = new URI("http://localhost:8084/friendsService/api/v1/friends/isFriends");

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LogManager.getLogger(FriendAdapter.class);

    public FriendAdapter() throws URISyntaxException {
    }

    @Override
    public Mono<Result<IsFriends>> isFriends(Mono<FriendData> friendsIds) {

        return friendsIds.flatMap(friendData -> {
            String uri = uriRequestIsFriends + "?friendFirstId=" + friendData.idFirstFriend() + "&friendSecondId=" + friendData.idSecondFriend();

            return WebClient.create().get().uri(uri)
                    .retrieve()
                    .onStatus(
                            HttpStatus.BAD_REQUEST::equals,
                            response -> {
                                logger.error("Bad request for friendship status: {}", response.statusCode());
                                return Mono.error(new RuntimeException("Error retrieving friendship status"));
                            }
                    )
                    .toEntity(String.class)
                    .flatMap(responseEntity -> {
                        try {
                            IsFriends isFriends = objectMapper.readValue(responseEntity.getBody(), IsFriends.class);
                            logger.info("Friendship status retrieved successfully");
                            return Mono.just(Result.success(isFriends));
                        } catch (JsonProcessingException e) {
                            logger.error("Error parsing friendship status response: {}", e.getMessage());
                            return Mono.error(new RuntimeException("Error retrieving friendship status"));
                        }
                    })
                    .onErrorResume(e -> {
                        logger.error("Error retrieving friendship status: {}", e.getMessage());
                        return Mono.just(Result.<IsFriends>error("Error retrieving friendship status"));
                    });
        });
    }
}
