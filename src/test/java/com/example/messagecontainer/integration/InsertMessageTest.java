package com.example.messagecontainer.integration;


import com.example.messagecontainer.entity.request.*;
import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.port.out.FiendServicePort;
import com.example.messagecontainer.port.out.UserServicePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URISyntaxException;
import java.util.Objects;
import static org.mockito.ArgumentMatchers.any;


public class InsertMessageTest extends DefaultTestConfiguration {

    @MockBean
    private UserServicePort userServicePort;
    @MockBean
    private FiendServicePort friendServicePort;

    @Test
    public void insertMessageShouldReturnCorrectStatusAndSavedNewMessageInDatabase() throws URISyntaxException {
        // given
        Long idFirstUser = 1L;
        Long idSecondUser = 2L;

        Mockito.when(userServicePort.getUserAboutId(any(Mono.class))).thenAnswer(invocation -> {
            Mono<IdUserData> monoIdUSer = invocation.getArgument(0);
            if (Objects.requireNonNull(monoIdUSer.block()).idUser() == 1L) {
                return Mono.just(Result.success(new UserData(idFirstUser, "User1", "User1", "User1")));
            }
            return Mono.just(Result.success(new UserData(idSecondUser, "User2", "User2", "User2")));
        });

        Mockito.when(friendServicePort.isFriends(any(Mono.class))).thenAnswer(invocation -> {
            Mono<FriendData> idsFriends = invocation.getArgument(0);
            if (Objects.equals(Objects.requireNonNull(idsFriends.block()).idFirstFriend(), idFirstUser) && Objects.equals(Objects.requireNonNull(idsFriends.block()).idSecondFriend(), idSecondUser)) {
                return Mono.just(Result.success(new IsFriends(true)));
            }
            return Mono.just(Result.success(new IsFriends(false)));
        });

        MessageData messageData = new MessageData("Hello", idFirstUser, idSecondUser);

        // when
        // then
        webTestClient.post().uri(createRequestUtil().createRequestInsertMessage())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(messageData))
                .exchange()
                .expectStatus().isOk()
                .expectBody();


        String sqlSelectAllMessages = "SELECT message FROM message_container_schema.message;";
        //then
        Flux<String> messagesInDb = databaseClient.sql(sqlSelectAllMessages)
                .map((row, metadata) -> row.get("message", String.class)).all();


        StepVerifier.create(messagesInDb)
                .expectNextMatches(message -> message.equals("Hello"))
                .expectComplete()
                .verify();

    }

    @Test
    public void ifAnyUserDoesNotExistShouldReturnUserDoesNotExist() throws URISyntaxException {
        // given
        Long idFirstUser = 1L;
        Long idSecondUser = 2L;

        Mockito.when(userServicePort.getUserAboutId(any(Mono.class))).thenAnswer(invocation -> Mono.empty());

        MessageData messageData = new MessageData("Hello", idFirstUser, idSecondUser);

        // when
        // then
        webTestClient.post().uri(createRequestUtil().createRequestInsertMessage())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(messageData))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.ErrorMessage").isEqualTo("User not found");

    }

    @Test
    public void ifUserAreNotFriendsRequestShouldReturnNotFriendsResponse() throws URISyntaxException {
        // given
        Long idFirstUser = 1L;
        Long idSecondUser = 2L;

        Mockito.when(userServicePort.getUserAboutId(any(Mono.class))).thenAnswer(invocation -> {
            Mono<IdUserData> monoIdUSer = invocation.getArgument(0);
            if (Objects.requireNonNull(monoIdUSer.block()).idUser() == 1L) {
                return Mono.just(Result.success(new UserData(idFirstUser, "User1", "User1", "User1")));
            }
            return Mono.just(Result.success(new UserData(idSecondUser, "User2", "User2", "User2")));
        });

        Mockito.when(friendServicePort.isFriends(any(Mono.class))).thenAnswer(invocation -> Mono.just(Result.success(new IsFriends(false))));

        MessageData messageData = new MessageData("Hello", idFirstUser, idSecondUser);

        // when
        // then
        webTestClient.post().uri(createRequestUtil().createRequestInsertMessage())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(messageData))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.ErrorMessage").isEqualTo("Not friends");;
    }


}
