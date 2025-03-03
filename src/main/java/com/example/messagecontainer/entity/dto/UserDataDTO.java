package com.example.messagecontainer.entity.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public record UserDataDTO(@Id Long id, @NotNull String name, @NotNull String surname, @NotNull String email) {
}
