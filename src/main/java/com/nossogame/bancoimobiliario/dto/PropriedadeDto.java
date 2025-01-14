package com.nossogame.bancoimobiliario.dto;

public class PropriedadeDto {

    private String id;

    private String nome;

    private double valorCompra;

    private double aluguelBase;

    private int numeroCasas;

    private boolean hotel;

    private double valorAluguelAtual;

    private boolean hipotecada;

    private String donoId;

    private String salaId;

    private String cor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getAluguelBase() {
        return aluguelBase;
    }

    public void setAluguelBase(double aluguelBase) {
        this.aluguelBase = aluguelBase;
    }

    public int getNumeroCasas() {
        return numeroCasas;
    }

    public void setNumeroCasas(int numeroCasas) {
        this.numeroCasas = numeroCasas;
    }

    public boolean isHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }

    public double getValorAluguelAtual() {
        return valorAluguelAtual;
    }

    public void setValorAluguelAtual(double valorAluguelAtual) {
        this.valorAluguelAtual = valorAluguelAtual;
    }

    public boolean isHipotecada() {
        return hipotecada;
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }

    public String getDonoId() {
        return donoId;
    }

    public void setDonoId(String donoId) {
        this.donoId = donoId;
    }

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
