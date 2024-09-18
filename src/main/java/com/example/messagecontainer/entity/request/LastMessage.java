package com.example.messagecontainer.entity.request;

import jakarta.validation.constraints.NotNull;
public record LastMessage(@NotNull Long idUser, @NotNull Long idLastMessageWithUser) {
}

