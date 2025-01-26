package com.nossogame.bancoimobiliario.dto;

import com.nossogame.bancoimobiliario.model.enuns.SorteReves;

public class CartaImpactoRequestDto {

    private String salaId;
    private int valor;

    private SorteReves tipo;

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public SorteReves getTipo() {
        return tipo;
    }

    public void setTipo(SorteReves tipo) {
        this.tipo = tipo;
    }
}
