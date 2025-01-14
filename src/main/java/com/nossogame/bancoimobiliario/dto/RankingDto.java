package com.nossogame.bancoimobiliario.dto;

import java.time.LocalDateTime;

public class RankingDto {

    private String jogadorNome;
    private double saldoFinal;
    private int numeroPropriedades;
    private LocalDateTime dataVitoria;

    public RankingDto() {

    }

    public RankingDto(String jogadorNome, double saldoFinal, int numeroPropriedades, LocalDateTime dataVitoria) {
        this.jogadorNome = jogadorNome;
        this.saldoFinal = saldoFinal;
        this.numeroPropriedades = numeroPropriedades;
        this.dataVitoria = dataVitoria;
    }

    // Getters e Setters
    public String getJogadorNome() {
        return jogadorNome;
    }

    public void setJogadorNome(String jogadorNome) {
        this.jogadorNome = jogadorNome;
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

