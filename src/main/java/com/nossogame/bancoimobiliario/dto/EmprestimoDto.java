package com.nossogame.bancoimobiliario.dto;

import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;

import java.time.LocalDateTime;

public class EmprestimoDto {

    private String id;
    private String jogadorOrigemId;
    private String jogadorDestinoId;
    private double valorContratado;
    private double valorDevolucao;

    private double saldoDevedor;

    private double taxaJuros;

    private StatusEmprestimo status;

    private LocalDateTime dataEmprestimo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJogadorOrigemId() {
        return jogadorOrigemId;
    }

    public void setJogadorOrigemId(String jogadorOrigemId) {
        this.jogadorOrigemId = jogadorOrigemId;
    }

    public String getJogadorDestinoId() {
        return jogadorDestinoId;
    }

    public void setJogadorDestinoId(String jogadorDestinoId) {
        this.jogadorDestinoId = jogadorDestinoId;
    }

    public double getValorContratado() {
        return valorContratado;
    }

    public void setValorContratado(double valorContratado) {
        this.valorContratado = valorContratado;
    }

    public double getValorDevolucao() {
        return valorDevolucao;
    }

    public void setValorDevolucao(double valorDevolucao) {
        this.valorDevolucao = valorDevolucao;
    }

    public double getSaldoDevedor() {
        return saldoDevedor;
    }

    public void setSaldoDevedor(double saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }
}
