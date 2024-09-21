package com.example.messagecontainer.entity.response;

import java.sql.Timestamp;

public record MessageData(Long idSender, Long idReceiver, Long idMessage, String message, Timestamp dateTimeMessage) {
}
