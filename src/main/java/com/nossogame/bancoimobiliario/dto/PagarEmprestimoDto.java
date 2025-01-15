package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PagarEmprestimoDto {

    @NotNull
    private String jogadorDestinoId;

    @Positive
    private double valor;

    @NotNull
    private String emprestimoId;

    public String getJogadorDestinoId() {
        return jogadorDestinoId;
    }

    public void setJogadorDestinoId(String jogadorDestinoId) {
        this.jogadorDestinoId = jogadorDestinoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEmprestimoId() {
        return emprestimoId;
    }

    public void setEmprestimoId(String emprestimoId) {
        this.emprestimoId = emprestimoId;
    }
}
