package com.nossogame.bancoimobiliario.dto;

public class ConstruirPropriedadeRequestDto {

    private String jogadorId;
    private String propriedadeId;

    public ConstruirPropriedadeRequestDto() {
    }

    public ConstruirPropriedadeRequestDto(String jogadorId, String propriedadeId) {
        this.jogadorId = jogadorId;
        this.propriedadeId = propriedadeId;
    }

    public String getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(String jogadorId) {
        this.jogadorId = jogadorId;
    }

    public String getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(String propriedadeId) {
        this.propriedadeId = propriedadeId;
    }
}
