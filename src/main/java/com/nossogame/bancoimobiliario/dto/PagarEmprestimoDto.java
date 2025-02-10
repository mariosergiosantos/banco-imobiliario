package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.Positive;

public record PagarEmprestimoDto(@Positive double valor) {
}
