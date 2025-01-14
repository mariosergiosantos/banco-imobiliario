package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SolicitarEmprestimoDto {

    @NotNull
    private String jogadorOrigemId;

    @NotNull
    private String jogadorDestinoId;

    @Positive
    private double valorContratado;

    @Positive
    private double valorAcordado;

}
