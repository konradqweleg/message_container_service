package com.example.messagecontainer.entity.response;

import java.sql.Timestamp;

public record MessageResponse(Long idFriend,Long idSender,Long idReceiver, String message,Long idMessage, Timestamp dateTimeMessage) {
}
