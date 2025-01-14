package com.nossogame.bancoimobiliario.dto;

public class CartaImpactoRequestDto {

    private String jogadorId;
    private int valor;

    public String getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(String jogadorId) {
        this.jogadorId = jogadorId;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
