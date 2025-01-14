package com.nossogame.bancoimobiliario.dto;

import com.nossogame.bancoimobiliario.model.enuns.StatusSala;

import java.time.LocalDateTime;
import java.util.List;

public class SalaDto {

    private String id;

    private LocalDateTime dataCriacao;

    private StatusSala status;

    private String administradorId;

    private List<JogadorDto> jogadores;

    private List<PropriedadeDto> propriedades;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusSala getStatus() {
        return status;
    }

    public void setStatus(StatusSala status) {
        this.status = status;
    }

    public String getAdministradorId() {
        return administradorId;
    }

    public void setAdministradorId(String administradorId) {
        this.administradorId = administradorId;
    }

    public List<JogadorDto> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<JogadorDto> jogadores) {
        this.jogadores = jogadores;
    }

    public List<PropriedadeDto> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(List<PropriedadeDto> propriedades) {
        this.propriedades = propriedades;
    }
}
