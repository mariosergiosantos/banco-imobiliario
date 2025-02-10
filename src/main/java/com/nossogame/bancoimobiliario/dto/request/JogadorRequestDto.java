package com.nossogame.bancoimobiliario.dto.request;

import jakarta.validation.constraints.NotNull;

public record JogadorRequestDto(@NotNull String salaId, @NotNull String nome) {
}
