package com.example.messagecontainer.integration;


import com.example.messagecontainer.entity.request.*;
import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.integration.request_util.RequestUtil;
import com.example.messagecontainer.port.out.DatabasePort;
import com.example.messagecontainer.port.out.FiendServicePort;
import com.example.messagecontainer.port.out.UserServicePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import java.net.URISyntaxException;
import java.util.Objects;
import static org.mockito.ArgumentMatchers.any;


public class InsertMessageTest extends DefaultTestConfiguration {

    @MockBean
    private UserServicePort userServicePort;
    @MockBean
    private FiendServicePort friendServicePort;

    @Test
    public void insertMessage() throws URISyntaxException {
        // given
        Mockito.when(userServicePort.getUserAboutId(any(Mono.class))).thenAnswer(invocation -> {
            Mono<IdUserData> mono = invocation.getArgument(0);
            if (Objects.requireNonNull(mono.block()).idUser() == 1L) {
                return Mono.just(Result.success(new UserData(1L, "User1", "User1", "User1")));
            }
            return Mono.just(Result.success(new UserData(2L, "User2", "User2", "User2")));
        });

        Mockito.when(friendServicePort.isFriends(any(Mono.class))).thenAnswer(invocation -> {
            Mono<FriendData> mono = invocation.getArgument(0);
            if (Objects.requireNonNull(mono.block()).idFirstFriend() == 1L && Objects.requireNonNull(mono.block()).idSecondFriend() == 2L) {
                return Mono.just(Result.success(new IsFriends(true)));
            }
            return Mono.just(Result.success(new IsFriends(false)));
        });

        MessageData messageData = new MessageData("Hello", 1L, 2L);

        // when
        // then
        webTestClient.post().uri(createRequestUtil().createRequestInsertMessage())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(messageData))
                .exchange()
                .expectStatus().isOk()
                .expectBody();

    }


}
