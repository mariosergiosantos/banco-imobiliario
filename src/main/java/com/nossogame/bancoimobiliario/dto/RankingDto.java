package com.nossogame.bancoimobiliario.dto;

import java.time.LocalDateTime;

public class RankingDto {

    private String nomeJogador;
    private double saldoFinal;
    private int numeroPropriedades;
    private LocalDateTime dataVitoria;

    public RankingDto() {

    }

    public RankingDto(String nomeJogador, double saldoFinal, int numeroPropriedades, LocalDateTime dataVitoria) {
        this.nomeJogador = nomeJogador;
        this.saldoFinal = saldoFinal;
        this.numeroPropriedades = numeroPropriedades;
        this.dataVitoria = dataVitoria;
    }

    // Getters e Setters
    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public int getNumeroPropriedades() {
        return numeroPropriedades;
    }

    public void setNumeroPropriedades(int numeroPropriedades) {
        this.numeroPropriedades = numeroPropriedades;
    }

    public LocalDateTime getDataVitoria() {
        return dataVitoria;
    }

    public void setDataVitoria(LocalDateTime dataVitoria) {
        this.dataVitoria = dataVitoria;
    }
}

