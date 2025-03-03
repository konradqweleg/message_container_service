package com.example.messagecontainer.entity.dto;

import jakarta.validation.constraints.NotNull;
public record LastMessageDTO(@NotNull Long idUser, @NotNull Long idLastMessageWithUser) {
}

