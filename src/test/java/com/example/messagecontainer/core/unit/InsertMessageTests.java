package com.example.messagecontainer.core.unit;


import com.example.messagecontainer.adapter.out.friend_service.FriendServiceAdapter;
import com.example.messagecontainer.entity.request.*;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserSender))
                .thenReturn(new UserData(idUserSender, "John", "Doe", "mail@mail.eu")));

        when(userServicePort.getUserAboutId(new IdUserDTO(idUserReceiver))
                .thenReturn(new UserData(idUserReceiver, "Jane", "Smith", "jane@mail.eu")));

        when(friendServicePort.isFriends(new FriendPairDTO(idUserSender, idUserReceiver)).thenReturn(new IsFriends(true)));


        //when
        Mono<Void> result = messagePort.insertMessage(new MessageDTO("Hello", idUserSender, idUserReceiver));


        //then

        StepVerifier.create(result)
                .verifyComplete();

        Mockito.verify(databasePort).insertMessage(new Message(anyLong(),"Hello", idUserSender, idUserReceiver, any()));


    }


}
