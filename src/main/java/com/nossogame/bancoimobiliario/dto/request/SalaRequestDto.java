package com.nossogame.bancoimobiliario.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SalaRequestDto {

    @NotNull
    @NotBlank
    private String nomeJogadorAdm;

    public SalaRequestDto(String nomeJogadorAdm) {
        this.nomeJogadorAdm = nomeJogadorAdm;
    }

    public String getNomeJogadorAdm() {
        return nomeJogadorAdm;
    }

    public void setNomeJogadorAdm(String nomeJogadorAdm) {
        this.nomeJogadorAdm = nomeJogadorAdm;
    }
}
