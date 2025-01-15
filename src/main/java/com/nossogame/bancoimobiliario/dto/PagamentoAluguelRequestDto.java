package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.NotNull;

public class PagamentoAluguelRequestDto {

    @NotNull
    private String propriedadeId;

    @NotNull
    private String jogadorPaganteId;

    public String getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(String propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public String getJogadorPaganteId() {
        return jogadorPaganteId;
    }

    public void setJogadorPaganteId(String jogadorPaganteId) {
        this.jogadorPaganteId = jogadorPaganteId;
    }
}
