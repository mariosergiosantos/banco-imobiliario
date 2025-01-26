package com.nossogame.bancoimobiliario.dto.request;

import com.nossogame.bancoimobiliario.model.enuns.TipoTransacao;
import jakarta.validation.constraints.NotNull;

public class TransacaoRequestDto {

    @NotNull
    private TipoTransacao tipoTransacao;

    private String propriedadeId;

    private String compradorId;

    private double valor;

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(String propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public String getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(String compradorId) {
        this.compradorId = compradorId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
