package com.example.messagecontainer.core.unit;

import com.example.messagecontainer.entity.dto.IdUserDTO;
import com.example.messagecontainer.entity.dto.MessageDataDTO;
import com.example.messagecontainer.exception.repository.RepositoryException;
import com.example.messagecontainer.model.Message;
import com.example.messagecontainer.port.in.MessagePort;
import com.example.messagecontainer.port.out.RepositoryPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.sql.Timestamp;

import static org.mockito.Mockito.when;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetMessagesBetweenUsersTests {

    @MockBean
    private RepositoryPort repositoryPort;

    @Autowired
    private MessagePort messagePort;
    @Test
    public void whenCorrectGetMessagesBetweenUsersDataShouldBeReturned() {
        //given
        Long idUserSender = 1L;
        Long idUserReceiver = 2L;

        when(repositoryPort.findAllMessagesBetweenUsers(idUserSender, idUserReceiver))
                .thenReturn(Flux.just(new Message(1L,"Hello", idUserSender, idUserReceiver,new Timestamp(System.currentTimeMillis()))));

        //when
        Flux<MessageDataDTO> result = messagePort.getMessagesBetweenUsers(new IdUserDTO(idUserSender), new IdUserDTO(idUserReceiver));

        //then
        StepVerifier.create(result)
                .expectNextMatches(messageDataDTO -> messageDataDTO.idSender().equals(idUserSender) && messageDataDTO.idReceiver().equals(idUserReceiver))
                .verifyComplete();
        Mockito.verify(repositoryPort, Mockito.times(1)).findAllMessagesBetweenUsers(idUserSender, idUserReceiver);
    }

    @Test
    public void whenRepositoryCauseErrorShouldReturnRepositoryException(){
        //given
        Long idUserSender = 1L;
        Long idUserReceiver = 2L;

        when(repositoryPort.findAllMessagesBetweenUsers(idUserSender, idUserReceiver))
                .thenReturn(Flux.error(new RepositoryException("Error")));

        //when
        Flux<MessageDataDTO> result = messagePort.getMessagesBetweenUsers(new IdUserDTO(idUserSender), new IdUserDTO(idUserReceiver));

        //then
        StepVerifier.create(result)
                .expectError(RepositoryException.class)
                .verify();
        Mockito.verify(repositoryPort, Mockito.times(1)).findAllMessagesBetweenUsers(idUserSender, idUserReceiver);
    }
}
