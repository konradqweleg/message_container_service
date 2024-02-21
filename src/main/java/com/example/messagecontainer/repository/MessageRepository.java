package com.example.messagecontainer.repository;


import com.example.messagecontainer.model.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;



public interface MessageRepository extends ReactiveCrudRepository<Message, Long>  {

}
