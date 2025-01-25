package com.nossogame.bancoimobiliario.dto.request;

import jakarta.validation.constraints.NotNull;

public class JogadorRequestDto {

    @NotNull
    private String nome;

    public JogadorRequestDto() {
    }

    public JogadorRequestDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
