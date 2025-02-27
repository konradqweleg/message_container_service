package com.example.messagecontainer.core.unit;


import com.example.messagecontainer.adapter.out.friend_service.FriendServiceAdapter;
import com.example.messagecontainer.entity.request.*;
import com.example.messagecontainer.exception.friend_service.UserAreNotFriendsException;
import com.example.messagecontainer.exception.user_service.UserNotFoundException;
import com.example.messagecontainer.model.Message;
import com.example.messagecontainer.port.in.MessagePort;
import com.example.messagecontainer.port.out.DatabasePort;
import com.example.messagecontainer.port.out.FiendServicePort;
import com.example.messagecontainer.port.out.UserServicePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InsertMessageTests {

    @MockBean
    private UserServicePort userServicePort;

    @MockBean
    private FiendServicePort friendServicePort;

    @MockBean
    private DatabasePort databasePort;

    @Autowired
    private MessagePort messagePort;

    @Test
    public void whenCorrectInsertMessageDataMessageShouldBeSaved() {
        //given
        Long idUserSender = 1L;
        Long idUserReceiver = 2L;

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserSender)))
                .thenReturn(Mono.just(new UserData(idUserSender, "John", "Doe", "mail@mail.eu")));

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserReceiver)))
                .thenReturn(Mono.just(new UserData(idUserReceiver, "Jane", "Smith", "jane@mail.eu")));

        when(friendServicePort.isFriends(new FriendPairDTO(idUserSender, idUserReceiver)))
                .thenReturn(Mono.just(new IsFriends(true)));

        when(databasePort.insertMessage(any()))
                .thenReturn(Mono.empty());

        //when
        Mono<Void> result = messagePort.insertMessage(new MessageDTO("Hello", idUserSender, idUserReceiver));

        //then
        StepVerifier.create(result)
                .verifyComplete();

        Mockito.verify(databasePort, Mockito.times(1)).insertMessage(any(Message.class));

    }

    @Test
    public void whenSenderUserNotFoundShouldReturnUserNotFoundException() {
        //given
        Long idUserSender = 1L;
        Long idUserReceiver = 2L;

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserSender)))
                .thenReturn(Mono.error(new UserNotFoundException("User not found")));

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserReceiver)))
                .thenReturn(Mono.just(new UserData(idUserReceiver, "John", "Doe", "mail@mail.eu")));

        when(friendServicePort.isFriends(new FriendPairDTO(idUserSender, idUserReceiver)))
                .thenReturn(Mono.just(new IsFriends(true)));

        when(databasePort.insertMessage(any()))
                .thenReturn(Mono.empty());

        //when
        Mono<Void> result = messagePort.insertMessage(new MessageDTO("Hello", idUserSender, idUserReceiver));

        //then
        StepVerifier.create(result)
                .expectError(UserNotFoundException.class).verify();

        Mockito.verify(databasePort, Mockito.times(0)).insertMessage(any(Message.class));
    }

    @Test
    public void whenReceiverUserNotFoundShouldReturnUserNotFoundException() {
        //given
        Long idUserSender = 1L;
        Long idUserReceiver = 2L;

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserSender)))
                .thenReturn(Mono.error(new UserNotFoundException("User not found")));

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserReceiver)))
                .thenReturn(Mono.just(new UserData(idUserReceiver, "John", "Doe", "mail@mail.eu")));

        when(friendServicePort.isFriends(new FriendPairDTO(idUserSender, idUserReceiver)))
                .thenReturn(Mono.just(new IsFriends(true)));

        when(databasePort.insertMessage(any()))
                .thenReturn(Mono.empty());

        //when
        Mono<Void> result = messagePort.insertMessage(new MessageDTO("Hello", idUserSender, idUserReceiver));

        //then
        StepVerifier.create(result)
                .expectError(UserNotFoundException.class).verify();

        Mockito.verify(databasePort, Mockito.times(0)).insertMessage(any(Message.class));
    }

    @Test
    public void whenUsersAreNotFriendsShouldReturnError() {
        //given
        Long idUserSender = 1L;
        Long idUserReceiver = 2L;

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserSender)))
                .thenReturn(Mono.just(new UserData(idUserSender, "John", "Doe", "mail@mail.eu")));

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserReceiver)))
                .thenReturn(Mono.just(new UserData(idUserReceiver, "Jane", "Smith", "jane@mail.eu")));

        when(friendServicePort.isFriends(new FriendPairDTO(idUserSender, idUserReceiver)))
                .thenReturn(Mono.just(new IsFriends(false)));

        //when
        Mono<Void> result = messagePort.insertMessage(new MessageDTO("Hello", idUserSender, idUserReceiver));

        //then
        StepVerifier.create(result)
                .expectError(UserAreNotFriendsException.class)
                .verify();

        Mockito.verify(databasePort, Mockito.never()).insertMessage(any(Message.class));
    }

}
