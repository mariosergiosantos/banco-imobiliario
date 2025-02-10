package com.nossogame.bancoimobiliario.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaRequestDto(@NotNull @NotBlank String nomeJogadorAdm) {
}
