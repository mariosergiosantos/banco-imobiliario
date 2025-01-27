package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PagarEmprestimoDto {

    @NotNull
    private String pagadorId;

    @Positive
    private double valor;

    public String getPagadorId() {
        return pagadorId;
    }

    public void setPagadorId(String pagadorId) {
        this.pagadorId = pagadorId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
