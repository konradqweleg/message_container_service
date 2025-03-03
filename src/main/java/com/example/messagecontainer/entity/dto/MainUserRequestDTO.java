package com.example.messagecontainer.entity.dto;

import java.util.List;

public record MainUserRequestDTO(List<LastMessageDTO> lastMessageDTOS) {
}