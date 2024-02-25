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

@Service
public class FriendAdapter implements FiendServicePort {

    private final URI uriRequestIsFriends = new URI("http://localhost:8084/friendsService/api/v1/friends/isFriends");

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FriendAdapter() throws URISyntaxException {
    }

    @Override
    public Mono<Result<IsFriends>> isFriends(Mono<FriendData> friendsIds) {
     return friendsIds.flatMap( friendData-> WebClient.create().get().uri(uriRequestIsFriends+"?friendFirstId=" + friendData.idFirstFriend().toString()+"&friendSecondId="+friendData.idSecondFriend().toString())
             .retrieve()
             .onStatus(
                     HttpStatus.BAD_REQUEST::equals,
                     response -> response.bodyToMono(String.class).map(Exception::new)
             )
             .toEntity(String.class)
             .flatMap(responseEntity -> {
                 try {
                     System.out.println(responseEntity.getBody());
                     IsFriends isFriends =  objectMapper.readValue(responseEntity.getBody(), IsFriends.class);
                     return Mono.just(Result.success(isFriends));
                 } catch (JsonProcessingException e) {
                     return Mono.error(new RuntimeException(e));
                 }

             })
             .onErrorResume(response -> Mono.<Result<IsFriends>>just(Result.<IsFriends>error(response.getMessage()))
             )
     );

    }
}
