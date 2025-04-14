package com.nossogame.bancoimobiliario.dto;

import com.nossogame.bancoimobiliario.model.enuns.TipoTransacao;

import java.time.LocalDateTime;

public class TransacaoDto {

    private String id;

    private String salaId;

    private JogadorDto origem;

    private JogadorDto destino;

    private PropriedadeDto propriedade;

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

    public JogadorDto getOrigem() {
        return origem;
    }

    public void setOrigem(JogadorDto origem) {
        this.origem = origem;
    }

    public JogadorDto getDestino() {
        return destino;
    }

    public void setDestino(JogadorDto destino) {
        this.destino = destino;
    }

    public PropriedadeDto getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(PropriedadeDto propriedade) {
        this.propriedade = propriedade;
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
