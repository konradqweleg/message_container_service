package com.example.messagecontainer.service;

import com.example.messagecontainer.entity.dto.*;
import com.example.messagecontainer.exception.friend_service.UserAreNotFriendsException;
import com.example.messagecontainer.model.Message;
import com.example.messagecontainer.port.in.MessagePort;
import com.example.messagecontainer.port.out.RepositoryPort;
import com.example.messagecontainer.port.out.FiendServicePort;
import com.example.messagecontainer.port.out.UserServicePort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;


@Service
public class MessageService implements MessagePort {

    private final RepositoryPort repositoryPort;

    private final UserServicePort userServicePort;

    private final FiendServicePort friendServicePort;

    private static final Logger logger = LogManager.getLogger(MessageService.class);

    public MessageService(RepositoryPort repositoryPort, UserServicePort userServicePort, FiendServicePort friendServicePort) {
        this.repositoryPort = repositoryPort;
        this.userServicePort = userServicePort;
        this.friendServicePort = friendServicePort;
    }


    @Override
    public Mono<Void> insertMessage(MessageDTO messageDTO) {
        return getUser(messageDTO.id_user_sender())
                .flatMap(userSender -> getUser(messageDTO.id_user_receiver())
                        .flatMap(userReceiver -> checkFriendship(messageDTO.id_user_sender(), messageDTO.id_user_receiver())
                                .flatMap(isFriendsDTO -> {
                                    if (!isFriendsDTO.areFriends()) {
                                        logger.error("Users are not friends: {} and {}", messageDTO.id_user_sender(), messageDTO.id_user_receiver());
                                        return Mono.error(new UserAreNotFriendsException("Users are not friends"));
                                    }
                                    return insertMessageIntoDatabase(messageDTO);
                                })));
    }

    private Mono<UserDataDTO> getUser(Long userId) {
        return userServicePort.getUserAboutId(new IdUserDTO(userId));
    }

    private Mono<IsFriendsDTO> checkFriendship(Long senderId, Long receiverId) {
        return friendServicePort.isFriends(new FriendPairDTO(senderId, receiverId));
    }

    private Mono<Void> insertMessageIntoDatabase(MessageDTO messageDTO) {
        return repositoryPort.insertMessage(new Message(null, messageDTO.message(), messageDTO.id_user_sender(), messageDTO.id_user_receiver(), new Timestamp(System.currentTimeMillis())))
                .doOnSuccess(insertedMessage -> logger.info("Message inserted successfully"))
                .then();
    }

    @Override
    public Flux<MessageDataDTO> getMessagesBetweenUsers(IdUserDTO idFirstUser, IdUserDTO idFriend) {
        return repositoryPort.findAllMessagesBetweenUsers(idFirstUser.idUser(), idFriend.idUser())
                .map(message -> new MessageDataDTO(
                        message.id_user_sender(),
                        message.id_user_receiver(),
                        message.id(),
                        message.message(),
                        message.date_time_message()
                ))
                .doOnComplete(() -> logger.info("Messages fetched successfully"));
    }


    @Override
    public Flux<MessageDataDTO> getLastMessagesWithFriendsForSpecificUser(IdUserDTO idUserData) {
        return repositoryPort.findLastMessagesWithFriendsByUserId(idUserData.idUser())
                .map(message -> new MessageDataDTO(
                        message.id_user_sender(),
                        message.id_user_receiver(),
                        message.id(),
                        message.message(),
                        message.date_time_message()
                ))
                .doOnComplete(() -> logger.info("Messages fetched successfully"));
    }


    @Override
    public Flux<MessageDataDTO> getMessagesWithFriendsFromId(IdUserDTO userId, MainUserRequestDTO mainUserRequestDTO) {
        return Flux.fromIterable(mainUserRequestDTO.lastMessageDTOS())
                .flatMap(lastMsg -> repositoryPort.findAllMessagesBetweenUsersSinceMessageId(userId.idUser(), lastMsg.idUser(), lastMsg.idLastMessageWithUser())
                        .map(message -> new MessageDataDTO(
                                message.id_user_sender(),
                                message.id_user_receiver(),
                                message.id(),
                                message.message(),
                                message.date_time_message()
                        ))
                        .doOnComplete(() -> logger.info("Messages fetched successfully")));
    }


}
