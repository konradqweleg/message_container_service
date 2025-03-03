package com.example.messagecontainer.entity.dto;

import java.sql.Timestamp;

public record MessageDataDTO(Long idSender, Long idReceiver, Long idMessage, String message, Timestamp dateTimeMessage) {
}
