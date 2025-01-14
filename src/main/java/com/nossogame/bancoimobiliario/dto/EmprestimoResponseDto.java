package com.nossogame.bancoimobiliario.dto;

public class EmprestimoResponseDto {

    private String id;
    private String jogadorOrigemId;
    private String jogadorDestinoId;
    private double valor;
    private Double taxaJuros;
    private String dataVencimento;
    private String status;

    public EmprestimoResponseDto(String id, String jogadorOrigemId, String jogadorDestinoId, double valor, Double taxaJuros, String dataVencimento, String status) {
        this.id = id;
        this.jogadorOrigemId = jogadorOrigemId;
        this.jogadorDestinoId = jogadorDestinoId;
        this.valor = valor;
        this.taxaJuros = taxaJuros;
        this.dataVencimento = dataVencimento;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getJogadorOrigemId() {
        return jogadorOrigemId;
    }

    public String getJogadorDestinoId() {
        return jogadorDestinoId;
    }

    public double getValor() {
        return valor;
    }

    public Double getTaxaJuros() {
        return taxaJuros;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public String getStatus() {
        return status;
    }
}
