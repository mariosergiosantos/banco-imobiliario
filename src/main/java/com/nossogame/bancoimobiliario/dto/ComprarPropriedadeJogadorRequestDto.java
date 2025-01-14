package com.nossogame.bancoimobiliario.dto;

public class ComprarPropriedadeJogadorRequestDto {

    private String idPropriedade;

    private String compradorId;

    private String vendedorId;

    private double valor;

    public String getIdPropriedade() {
        return idPropriedade;
    }

    public void setIdPropriedade(String idPropriedade) {
        this.idPropriedade = idPropriedade;
    }

    public String getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(String compradorId) {
        this.compradorId = compradorId;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
