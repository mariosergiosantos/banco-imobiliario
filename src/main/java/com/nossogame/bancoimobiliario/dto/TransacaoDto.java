package com.nossogame.bancoimobiliario.dto;

import com.nossogame.bancoimobiliario.model.enuns.TipoTransacao;

import java.time.LocalDateTime;

public class TransacaoDto {

    private String id;

    private String salaId;

    private String origemId;

    private String destinoId;

    private String propriedadeId;

    private double valor;

    private TipoTransacao tipo;

    private LocalDateTime dataHora;

    private String descricao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public String getOrigemId() {
        return origemId;
    }

    public void setOrigemId(String origemId) {
        this.origemId = origemId;
    }

    public String getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(String destinoId) {
        this.destinoId = destinoId;
    }

    public String getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(String propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
