package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PagarEmprestimoDto {

    @NotNull
    private String jogadorDestinoId;

    @Positive
    private double valor;

    @NotNull
    private String emprestimoId;
}
