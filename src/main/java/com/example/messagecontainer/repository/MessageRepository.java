package com.example.messagecontainer.repository;


import com.example.messagecontainer.model.Message;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


public interface MessageRepository extends ReactiveCrudRepository<Message, Long>  {




    @Query("SELECT DISTINCT ON (id_user_sender, id_user_receiver) id, message, id_user_sender, id_user_receiver, date_time_message " +
            "FROM message_container_schema.message "+
            "WHERE id_user_sender = :idUser OR id_user_receiver = :idUser "+
            "ORDER BY id_user_sender, id_user_receiver, date_time_message DESC;")
    Flux<Message> getLastMessagesWithEachFriendsForSpecificUser(Long idUser);

}
