package com.example.messagecontainer.repository;


import com.example.messagecontainer.model.Message;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface MessageRepository extends ReactiveCrudRepository<Message, Long>  {

    @Query("SELECT DISTINCT ON (id_user_sender, id_user_receiver) id, message, id_user_sender, id_user_receiver, date_time_message " +
            "FROM message_container_schema.message "+
            "WHERE id_user_sender = :idUser OR id_user_receiver = :idUser "+
            "ORDER BY id_user_sender, id_user_receiver, date_time_message DESC;")
    Flux<Message> findDistinctLastMessagesWithFriendsByUserId(Long idUser);

    @Query("SELECT * FROM message_container_schema.message WHERE id_user_sender = :idFirstUser AND id_user_receiver = :idSecondUser OR id_user_sender = :idSecondUser AND id_user_receiver = :idFirstUser")
    Flux<Message> findAllMessagesBetweenUsers(Long idFirstUser, Long idSecondUser);

    @Query("SELECT * FROM message_container_schema.message WHERE (id_user_sender = :idFirstUser AND id_user_receiver = :idSecondUser OR id_user_sender = :idSecondUser AND id_user_receiver = :idFirstUser) AND id > :messageId")
    Flux<Message> findAllMessagesBetweenUsersSinceMessageId(Long idFirstUser, Long idSecondUser, Long messageId);


}
