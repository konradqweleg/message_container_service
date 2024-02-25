package com.example.messagecontainer.service;

import com.example.messagecontainer.entity.request.FriendData;
import com.example.messagecontainer.entity.request.IdUserData;
import com.example.messagecontainer.entity.request.MessageData;
import com.example.messagecontainer.entity.response.Result;
import com.example.messagecontainer.entity.response.Status;
import com.example.messagecontainer.model.Message;
import com.example.messagecontainer.port.in.MessagePort;
import com.example.messagecontainer.port.out.DatabasePort;
import com.example.messagecontainer.port.out.FiendServicePort;
import com.example.messagecontainer.port.out.UserServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@Service
public class MessageService implements MessagePort {

    private final DatabasePort databasePort;

    private final UserServicePort userServicePort;

    private final FiendServicePort friendServicePort;

    public MessageService(DatabasePort databasePort, UserServicePort userServicePort, FiendServicePort friendServicePort) {
        this.databasePort = databasePort;
        this.userServicePort = userServicePort;
        this.friendServicePort = friendServicePort;
    }

    @Override
    public Mono<Result<Status>> insertMessage(Mono<MessageData> message) {

        return message.flatMap(messageData-> userServicePort.getUserAboutId(Mono.just(new IdUserData(messageData.id_user_sender())))
                .flatMap(userSender -> userServicePort.getUserAboutId(Mono.just(new IdUserData(messageData.id_user_receiver())))
                                .flatMap(userReceiver -> friendServicePort.isFriends(Mono.just(new FriendData(messageData.id_user_sender(),messageData.id_user_receiver())))
                                        .flatMap(isFriends -> {
                                            if(!isFriends.getValue().areFriends()){
                                                return Mono.just(Result.<Status>error("Not friends"));
                                            }

                                            if (userSender.isSuccess() && userReceiver.isSuccess() && isFriends.isSuccess()  && isFriends.getValue().areFriends()) {
                                                return databasePort.insertMessage(new Message(null, messageData.message(), messageData.id_user_sender(),messageData.id_user_receiver(),new Timestamp(System.currentTimeMillis())))
                                                        .map(message1 -> Result.success(new Status(true)));
                                            } else {
                                                return Mono.just(Result.<Status>error("User not found"));
                                            }
                                        })
                                )

                )
                .switchIfEmpty(Mono.just(Result.<Status>error("User not found")))
        );

    }
}
