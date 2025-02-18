package com.example.messagecontainer.entity.request;

import jakarta.validation.constraints.NotNull;

public record MessageDTO(@NotNull String message, @NotNull Long id_user_sender, @NotNull Long id_user_receiver) {
}
