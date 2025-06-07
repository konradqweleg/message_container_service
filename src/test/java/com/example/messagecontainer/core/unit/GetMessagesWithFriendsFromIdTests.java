package com.example.messagecontainer.core.unit;

import com.example.messagecontainer.entity.dto.IdUserDTO;
import com.example.messagecontainer.entity.dto.LastMessageDTO;
import com.example.messagecontainer.entity.dto.MainUserRequestDTO;
import com.example.messagecontainer.entity.dto.MessageDataDTO;
import com.example.messagecontainer.model.Message;
import com.example.messagecontainer.port.in.MessagePort;
import com.example.messagecontainer.port.out.RepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.sql.Timestamp;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetMessagesWithFriendsFromIdTests {
    @MockBean
    private RepositoryPort repositoryPort;

    @Autowired
    private MessagePort messagePort;

    @Test
    public void whenCorrectGetMessagesWithFriendsFromIdDataShouldBeReturned() {
        //given
        Long idFirstUser = 1L;
        Long idSecondUser = 2L;

        when(repositoryPort.findAllMessagesBetweenUsersSinceMessageId(idFirstUser, idSecondUser, 1L))
                .thenReturn(Flux.just(new Message(1L, "Hello", idFirstUser, idSecondUser, new Timestamp(System.currentTimeMillis()))));

        //when
        LastMessageDTO lastMessageDTO = new LastMessageDTO(idSecondUser,1L);
        MainUserRequestDTO mainUserRequestDTO = new MainUserRequestDTO(List.of(lastMessageDTO));
        Flux<MessageDataDTO> result = messagePort.getMessagesWithFriendsFromId(new IdUserDTO(idFirstUser),mainUserRequestDTO);

        //then
        StepVerifier.create(result)
                .expectNextMatches(messageDataDTO -> messageDataDTO.idSender().equals(idFirstUser) && messageDataDTO.idReceiver().equals(2L))
                .verifyComplete();
    }
}
