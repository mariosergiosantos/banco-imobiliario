package com.nossogame.bancoimobiliario.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SolicitarEmprestimoDto {

    private String salaId;

    @NotNull
    private String recebedorId;

    @NotNull
    private String pagadorId;

    @Positive
    private double valorContratado;

    //valorAPagar
    @Positive
    private double valorAcordado;

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public String getRecebedorId() {
        return recebedorId;
    }

    public void setRecebedorId(String recebedorId) {
        this.recebedorId = recebedorId;
    }

    public String getPagadorId() {
        return pagadorId;
    }

    public void setPagadorId(String pagadorId) {
        this.pagadorId = pagadorId;
    }

    public double getValorContratado() {
        return valorContratado;
    }

    public void setValorContratado(double valorContratado) {
        this.valorContratado = valorContratado;
    }

    public double getValorAcordado() {
        return valorAcordado;
    }

    public void setValorAcordado(double valorAcordado) {
        this.valorAcordado = valorAcordado;
    }
}
