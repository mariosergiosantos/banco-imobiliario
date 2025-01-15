package com.nossogame.bancoimobiliario.dto;

public class VencedorJogoDto {

    private String salaId;

    private String nome;

    private double saldo;

    private double saldoPropriedades;

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldoPropriedades() {
        return saldoPropriedades;
    }

    public void setSaldoPropriedades(double saldoPropriedades) {
        this.saldoPropriedades = saldoPropriedades;
    }
}
