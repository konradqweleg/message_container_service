package com.example.messagecontainer.entity.response;

public record LastMessageData(Long idSender, Long idReceiver, Long idMessage, String message, String dateTimeMessage) {
}
