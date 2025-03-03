package com.example.messagecontainer.port.in;

import com.example.messagecontainer.entity.dto.IdUserDTO;
import com.example.messagecontainer.entity.dto.MainUserRequestDTO;
import com.example.messagecontainer.entity.dto.MessageDTO;
import com.example.messagecontainer.entity.dto.MessageDataDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MessagePort {
    Mono<Void> insertMessage(MessageDTO message);
    Flux<MessageDataDTO> getMessagesBetweenUsers(IdUserDTO idFirstUserMono, IdUserDTO idFriendMono);
    Flux<MessageDataDTO> getLastMessagesWithFriendsForSpecificUser(IdUserDTO idUserDTOMono);
    Flux<MessageDataDTO> getMessagesWithFriendsFromId(IdUserDTO idUserDTO, MainUserRequestDTO lastMessage);

}
