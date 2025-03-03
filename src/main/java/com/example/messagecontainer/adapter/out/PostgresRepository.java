package com.example.messagecontainer.adapter.out;

import com.example.messagecontainer.exception.repository.RepositoryException;
import com.example.messagecontainer.model.Message;
import com.example.messagecontainer.port.out.RepositoryPort;
import com.example.messagecontainer.repository.MessageRepository;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostgresRepository implements RepositoryPort {


    private final MessageRepository messageRepository;
    private final Logger logger = LogManager.getLogger(PostgresRepository.class);

    public PostgresRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Mono<Message> insertMessage(Message message) {
        return messageRepository.save(message).onErrorResume(e -> {
            logger.error("Error inserting message: {}", message, e);
            return Mono.error(new RepositoryException("Error inserting message"));
        });
    }

    @Override
    public Flux<Message> findLastMessagesWithFriendsByUserId(Long idUser) {
        return messageRepository.findDistinctLastMessagesWithFriendsByUserId(idUser).onErrorResume(e -> {
            logger.error("Error fetching last messages with friends for user: {}", idUser, e);
            return Flux.error(new RepositoryException("Error fetching last messages with friends for user"));
        });
    }

    @Override
    public Flux<Message> findAllMessagesBetweenUsers(Long idFirstUser, Long idSecondUser) {
        return messageRepository.findAllMessagesBetweenUsers(idFirstUser,idSecondUser).onErrorResume(e -> {
            logger.error("Error fetching messages between users: {} and {}", idFirstUser, idSecondUser, e);
            return Flux.error(new RepositoryException("Error fetching messages between users"));
        });
    }

    @Override
    public Flux<Message> findAllMessagesBetweenUsersSinceMessageId(Long idFirstUser, Long idSecondUser, Long messageId){
        return messageRepository.findAllMessagesBetweenUsersSinceMessageId(idFirstUser,idSecondUser,messageId).onErrorResume(e -> {
            logger.error("Error fetching messages between users: {} and {} since message ID: {}", idFirstUser, idSecondUser, messageId, e);
            return Flux.error(new RepositoryException("Error fetching messages between users since message ID"));
        });
    }


}
