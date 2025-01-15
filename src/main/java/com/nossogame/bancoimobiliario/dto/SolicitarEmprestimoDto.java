package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SolicitarEmprestimoDto {

    @NotNull
    private String jogadorOrigemId;

    @NotNull
    private String jogadorDestinoId;

    @Positive
    private double valorContratado;

    @Positive
    private double valorAcordado;


    public String getJogadorOrigemId() {
        return jogadorOrigemId;
    }

    public void setJogadorOrigemId(String jogadorOrigemId) {
        this.jogadorOrigemId = jogadorOrigemId;
    }

    public String getJogadorDestinoId() {
        return jogadorDestinoId;
    }

    public void setJogadorDestinoId(String jogadorDestinoId) {
        this.jogadorDestinoId = jogadorDestinoId;
    }

    public double getValorContratado() {
        return valorContratado;
    }

    public void setValorContratado(double valorContratado) {
        this.valorContratado = valorContratado;
    }

    public double getValorAcordado() {
        return valorAcordado;
    }

    public void setValorAcordado(double valorAcordado) {
        this.valorAcordado = valorAcordado;
    }
}
