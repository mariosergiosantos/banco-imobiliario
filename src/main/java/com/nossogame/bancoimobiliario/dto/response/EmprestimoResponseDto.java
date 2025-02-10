package com.nossogame.bancoimobiliario.dto.response;

public class EmprestimoResponseDto {

    private String id;
    private String recebedorId;
    private String pagadorId;
    private double valor;
    private Double taxaJuros;
    private String dataVencimento;
    private String status;

    public EmprestimoResponseDto(String id, String recebedorId, String pagadorId, double valor, Double taxaJuros, String dataVencimento, String status) {
        this.id = id;
        this.recebedorId = recebedorId;
        this.pagadorId = pagadorId;
        this.valor = valor;
        this.taxaJuros = taxaJuros;
        this.dataVencimento = dataVencimento;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getRecebedorId() {
        return recebedorId;
    }

    public String getPagadorId() {
        return pagadorId;
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
