package com.nossogame.bancoimobiliario.dto;

@Deprecated
public class ComprarPropriedadeBancoRequestDto {

    private String idPropriedade;

    private String jogadorId;

    public String getIdPropriedade() {
        return idPropriedade;
    }

    public void setIdPropriedade(String idPropriedade) {
        this.idPropriedade = idPropriedade;
    }

    public String getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(String jogadorId) {
        this.jogadorId = jogadorId;
    }
}
