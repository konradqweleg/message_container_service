package com.example.messagecontainer.service;

import com.example.messagecontainer.entity.request.MessageData;
import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.entity.response.Status;
import com.example.messagecontainer.model.Message;
import com.example.messagecontainer.port.in.MessagePort;
import com.example.messagecontainer.port.out.DatabasePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@Service
public class MessageService implements MessagePort {

    private final DatabasePort databasePort;

    public MessageService(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    @Override
    public Mono<Result<Status>> insertMessage(Mono<MessageData> message) {
       return message.map(messageData-> new Message(null, messageData.message(), messageData.id_user_sender(),messageData.id_user_receiver(),new Timestamp(System.currentTimeMillis())))
               .flatMap(databasePort::insertMessage)
               .map(message1 -> Result.success(new Status(true))).onErrorResume(throwable -> Mono.just(Result.error(throwable.getMessage())));
    }
}
