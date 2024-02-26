package com.example.messagecontainer.entity.response;

import java.sql.Timestamp;

public record MessageResponse(Long idFriend, String message, Timestamp dateTimeMessage) {
}
