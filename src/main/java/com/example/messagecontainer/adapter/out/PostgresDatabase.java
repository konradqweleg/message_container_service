package com.example.messagecontainer.adapter.out;

import com.example.messagecontainer.model.Message;
import com.example.messagecontainer.port.out.DatabasePort;

import com.example.messagecontainer.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PostgresDatabase implements DatabasePort {


    private final MessageRepository messageRepository;

    public PostgresDatabase(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Mono<Message> insertMessage(Message message) {
        return messageRepository.save(message);
    }
}
