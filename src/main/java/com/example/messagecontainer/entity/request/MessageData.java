package com.example.messagecontainer.entity.request;

public record MessageData(String message, Long id_user_sender, Long id_user_receiver) {
}
