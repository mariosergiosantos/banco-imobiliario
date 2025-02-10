package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SolicitarEmprestimoDto(String salaId, @NotNull String recebedorId, @NotNull String pagadorId,
                                     @Positive double valorContratado, @Positive double valorAcordado) {
}
