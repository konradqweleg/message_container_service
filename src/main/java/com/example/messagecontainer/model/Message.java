package com.example.messagecontainer.model;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

public record Message(@Id Long id, String message, Long id_user_sender, Long id_user_receiver, Timestamp date_time_message){

}
